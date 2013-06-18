sap.ui.controller("personslist-web-vic.personslist", {

	onInit: function() {
		var sOrigin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port : "");
		var personsListOdataServiceUrl = sOrigin + "/personslist-web-vic/personslist.svc";

		var odataModel = new sap.ui.model.odata.ODataModel(personsListOdataServiceUrl);
		this.getView().setModel(odataModel);
	},

	addNewPerson: function(sFirstName, sLastName, oTable) {
		var persons = {};

		persons.FirstName = sFirstName;
		persons.LastName = sLastName;

		this.getView().getModel().create("/Persons", persons, null, this.successMsg, this.errorMsg);
	},

	successMsg: function() {
		alert("Successfully added a person entity");
	},

	errorMsg: function() {
		alert("Error occured when trying to add a person entity");
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