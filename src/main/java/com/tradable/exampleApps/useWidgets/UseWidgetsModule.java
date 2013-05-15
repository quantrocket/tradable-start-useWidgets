package com.tradable.exampleApps.useWidgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tradable.api.component.WorkspaceModule;
import com.tradable.api.component.WorkspaceModuleProperties;
import com.tradable.api.component.state.PersistedStateHolder;
import com.tradable.api.entities.Instrument;
import com.tradable.api.entities.OrderSide;


import com.tradable.api.services.instrument.InstrumentService;
import com.tradable.api.services.marketdata.QuoteTickService;
import com.tradable.api.services.marketdata.QuoteTickSubscription;
import com.tradable.api.ui.LookAndFeelProperty;
import com.tradable.api.ui.widgets.autocomplete.model.AutoCompleteModel;
import com.tradable.api.ui.widgets.selector.SelectionListener;
import com.tradable.api.widgets.InstrumentSelector;
import com.tradable.ui.*;

import javax.swing.SwingConstants;

public class UseWidgetsModule extends JPanel implements WorkspaceModule{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //change this number to something more sensible
	
	private InstrumentService instrumentService;
	private AutoCompleteModel autoCompleteModel;
	private InstrumentSelector symbolField;
	private Instrument selectedSymbol; 
	private BuySellSelector buySellSelector;
	private OrderSide orderSide;
	private DynamicPriceModule dynamicPriceModule;

	
	public UseWidgetsModule(InstrumentService instrumentService,
			AutoCompleteModel autoCompleteModel,
			QuoteTickService quoteTickService) {
		setLayout(null);
		setSize(350, 150);
		
		////////////////////////////////////////////////////////////////////////
		//the following fields show the use of:
		//com.tradable.api.ui.LookAndFeelProperty;
		
		//This will be the first button we implement. We use the
		//putClientProperty from the JButton class to set our UI_STYLE
		JButton buyButton = new JButton("BUY");
		buyButton.setBounds(10, 10, 130, 30);
		buyButton.putClientProperty(LookAndFeelProperty.UI_STYLE, LookAndFeelProperty.BUY_BUTTON_STYLE);
		add(buyButton);
		
		JButton sellButton = new JButton("Sell");
		sellButton.setBounds(150, 10, 130, 30);
		sellButton.putClientProperty(LookAndFeelProperty.UI_STYLE, LookAndFeelProperty.SELL_BUTTON_STYLE);
		add(sellButton);
			
		JButton primaryButton = new JButton("Primary");
		primaryButton.setBounds(10, 50, 130, 30);
		primaryButton.putClientProperty(LookAndFeelProperty.UI_STYLE, LookAndFeelProperty.PRIMARY_BUTTON_STYLE);
		add(primaryButton);
		
		JButton secondaryButton = new JButton("Secondary");
		secondaryButton.setBounds(150, 50, 130, 30);
		secondaryButton.putClientProperty(LookAndFeelProperty.UI_STYLE, LookAndFeelProperty.SECONDARY_BUTTON_STYLE);
		add(secondaryButton);
		
		JTextField hintTextField = new JTextField();
		hintTextField.setHorizontalAlignment(SwingConstants.CENTER);
		hintTextField.setBounds(290, 10, 130, 30);
        hintTextField.putClientProperty(LookAndFeelProperty.TEXT_FIELD_HINT, "Hint text");
        add(hintTextField);
        ////////////////////////////////////////////////////////////////////////
		
        ////////////////////////////////////////////////////////////////////////
    	//used for the instrumentSelector
        // Setup of Symbol Selector is completed in 
        // #initialize() method after all services are installed
        this.instrumentService = instrumentService;
        this.autoCompleteModel = autoCompleteModel;
        symbolField = new InstrumentSelector();
        symbolField.setBounds(290, 50, 130, 30);
        symbolField.setInstrumentService(this.instrumentService);
        symbolField.setAutoCompleteModel(this.autoCompleteModel);
        symbolField.addSelectionListener(new SelectionListener<Instrument>() {

        	@Override
        		public void selectionChanged(Instrument selectedValue) {
        		//We here set our selectedSymbol object to the updated Value.
        		//You can then use this selectedSymbol to send orders or for any other purpose.
        		selectedSymbol = selectedValue;
        		
        		//this is used for the dynamicPriceModule object from line
        		//142
        		dynamicPriceModule.setInstrument(selectedSymbol);
        	}
        });

        symbolField.initialize();
        add(symbolField);
        ////////////////////////////////////////////////////////////////////////
        
		////////////////////////////////////////////////////////////////////////
		//the following fields show the use of:
		//com.tradable.api.ui
        
        //you can easily change those values so that the widget
        //comes up in a different state when it appears
        boolean isEnabled = true;
        orderSide = OrderSide.SELL;
        buySellSelector = new BuySellSelector(orderSide);
        //buySellSelector.setOrderSide(orderSide); //change the OrderSide using this.
        buySellSelector.setEnabled(isEnabled);
        buySellSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Perform some action when OrderSide is changed. 
				//e.g. update internal OrderSide object
				orderSide = buySellSelector.getOrderSide();		
			}
        	
        });
        buySellSelector.setBounds(10, 90, 130, 30);
        add(buySellSelector);
        
        StateSwitch stateSwitch = new StateSwitch(false);
        //you can the use setState() and getState to get and set state of switch
		stateSwitch.setBounds(150, 90, 69, 27);
        add(stateSwitch);      
        
        //see line 104 to see how to set the symbol occur
        dynamicPriceModule = new DynamicPriceModule(quoteTickService);
        dynamicPriceModule.setBounds(10, 130, 250, 28);
        add(dynamicPriceModule);
        
        //You can add a mouseListener to the placeOrderButton to have it
        //send any type of order when it is pressed.
        PlaceOrderButton placeOrderButton = new PlaceOrderButton();
        placeOrderButton.setBounds(10, 168, 150, 50);
        add(placeOrderButton);
        
        ////////////////////////////////////////////////////////////////////////
        
        
        
		setModuleTitle("Use Widgets");
	}
	
    
	@Override
	public JComponent getVisualComponent() {
		return this;
	}

	@Override
	public void destroy() {
        if (symbolField != null) {
            symbolField.destroy();
        }
		
	}

	@Override
	public PersistedStateHolder getPersistedState() {
		return null;
	}

	@Override
	public void loadPersistedState(PersistedStateHolder state) {
		
	}
	

    protected void setModuleTitle(String title) {
        putClientProperty(WorkspaceModuleProperties.COMPONENT_TITLE, title);
    }
    
}