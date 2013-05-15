package com.tradable.exampleApps.useWidgets;

import org.springframework.beans.factory.annotation.Autowired;

import com.tradable.api.component.WorkspaceModule;
import com.tradable.api.component.WorkspaceModuleCategory;
import com.tradable.api.component.WorkspaceModuleFactory;
import com.tradable.api.services.instrument.InstrumentService;
import com.tradable.api.services.marketdata.QuoteTickService;
import com.tradable.api.ui.widgets.autocomplete.model.AutoCompleteModel;

public class UseWidgetsFactory implements WorkspaceModuleFactory {


	///////////////////////////////////////////////////////////
	//used for the instrumentSelector
	@Autowired
    private InstrumentService instrumentService;

    @Autowired
    private AutoCompleteModel autoCompleteModel;
    
    @Autowired
    private QuoteTickService quoteTickService;
	///////////////////////////////////////////////////////////

	@Override
	public WorkspaceModuleCategory getCategory() {
		return WorkspaceModuleCategory.MISCELLANEOUS;
	}

	@Override
	public String getDisplayName() {
		// rename me
		return "Use Widgets";
	}

	@Override
	public String getFactoryId() {
		// TODO Auto-generated method stub
		return "com.tradable.exampleApps.useWidgets";
	}

	@Override
	public WorkspaceModule createModule() {
		// TODO Auto-generated method stub
		return new UseWidgetsModule(instrumentService, autoCompleteModel,
				quoteTickService);
	}

}
