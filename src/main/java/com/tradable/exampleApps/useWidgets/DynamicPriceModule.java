package com.tradable.exampleApps.useWidgets;

import com.tradable.api.entities.Instrument;
import com.tradable.api.services.marketdata.Quote;
import com.tradable.api.services.marketdata.QuoteTickEvent;
import com.tradable.api.services.marketdata.QuoteTickListener;
import com.tradable.api.services.marketdata.QuoteTickService;
import com.tradable.api.services.marketdata.QuoteTickSubscription;
import com.tradable.ui.PriceModule;


//This class just extends the PriceModule class and
//sets the price of the module whenever a price change is
//observed
public class DynamicPriceModule extends PriceModule implements
QuoteTickListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QuoteTickService quoteTickService;
	private QuoteTickSubscription tickSubscription;
	private Instrument instrument;
	private Quote bid;
	private Quote ask;
	
	
	public DynamicPriceModule(QuoteTickService quoteTickService){
		
		this.quoteTickService = quoteTickService;
		tickSubscription = quoteTickService.createSubscription();
	    tickSubscription.setListener(this);
	}
	
	public void destroy(){
		tickSubscription.setListener(null);
		tickSubscription.destroy();
	}
	
	public void setCurrentQuotes(){
        ask = tickSubscription.getAsk(instrument.getSymbol());            
        bid = tickSubscription.getBid(instrument.getSymbol());
		this.setPrice(bid.getPrice(), ask.getPrice());
	}
	
	public void setInstrument(Instrument instrument){
		this.instrument = instrument;
		super.setInstrument(this.instrument);
		tickSubscription.setSymbol(instrument.getSymbol());
		this.setCurrentQuotes();
	}
	
	@Override
	public void quotesUpdated(QuoteTickEvent event) {	
		this.setCurrentQuotes();
	}
}
