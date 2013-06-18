sap.ui.controller("personslist-web-vic.personslist", {

	onInit: function() {
		var oPersonsModel = new sap.ui.model.json.JSONModel();

		oPersonsModel.setData({
			Persons: [ {
				FirstName: "",
				LastName: ""
			} ]
		});

		this.getView().setModel(oPersonsModel);

	},

	addNewPerson: function(sFirstName, sLastName, oTable) {
		var oPersonsModel = new sap.ui.model.json.JSONModel();
		oPersonsModel.setData({
			Persons: [ {
				FirstName: sFirstName,
				LastName: sLastName
			} ]
		});

		this.getView().setModel(oPersonsModel);
		oTable.unbindRows().bindRows("/Persons");
	},
/**
 * Similar to onAfterRendering, but this hook is invoked before the controller's View is re-rendered (NOT before the first rendering! onInit() is used for that
 * one!).
 */
// onBeforeRendering: function() {
//
// },
/**
 * Called when the View has been rendered (so its HTML is part of the document). Post-rendering manipulations of the HTML could be done here. This hook is the
 * same one that SAPUI5 controls get after being rendered.
 */
// onAfterRendering: function() {
//
// },
/**
 * Called when the Controller is destroyed. Use this one to free resources and finalize activities.
 */
// onExit: function() {
//
// }
});