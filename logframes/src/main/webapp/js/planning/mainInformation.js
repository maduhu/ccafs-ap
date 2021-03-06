$(document).ready(function() {

  // Set the event to radio button
  $(".genderIntegrationOption").on("change", toggleGenderIntegrationText);
  // Hide the genderIntegration area if the radio has value 'no'
  toggleGenderIntegrationText();

  $(".removeContactPerson").on("click", function(event) {
    event.preventDefault();
    $(event.target).parent().parent().hide("slow", function() {
      $(event.target).parent().parent().remove();
      renameContactPersons();
    });
  });

  $(".addContactPerson").on("click", function(event) {
    event.preventDefault();
    var newContactPerson = $("#contactPersonTemplate").clone(true);
    $("#contactPersonBlock").append(newContactPerson);
    $(newContactPerson).attr("class", "contactPerson");
    renameContactPersons();
    newContactPerson.show("slow");
  });
  
  // Set text 'No funds' in budget percentage list
  var nofundsMsg = $("#activity\\.budget\\.noFunds").val();
  $("#mainInformation_activity_budget_cgFund option").first().text(nofundsMsg);
  $("#mainInformation_activity_budget_bilateral option").first().text(nofundsMsg);
  
  // Format the budget field
  $("#activity\\.budget\\.usd").on("keypress", isNumber);

  // Add chosen plugin to the milestone select
  $(".milestones").chosen();
  datePickerConfig();
});

function renameContactPersons() {
  $(".contactPerson").each(
      function(index, contactPerson) {
        // Block id
        $(this).attr("id", "contactPerson-" + index);
        // Contact person id
        $(this).find("[name$='id']").attr("id",
            "activity.contactPersons[" + index + "].id");
        $(this).find("[name$='id']").attr("name",
            "activity.contactPersons[" + index + "].id");
        // Contact name
        $(this).find("[id$='name']").attr("id",
            "activity.contactPersons[" + index + "].name");
        $(this).find("[name$='name']").attr("name",
            "activity.contactPersons[" + index + "].name");
        // Contact email
        $(this).find("[id$='email']").attr("id",
            "activity.contactPersons[" + index + "].email");
        $(this).find("[name$='email']").attr("name",
            "activity.contactPersons[" + index + "].email");
      });
}

/**
 * Hide or show the gender integration text area according to the value of radio
 * 'genderIntegrationOption'
 * 
 */
function toggleGenderIntegrationText() {
  if ($(".genderIntegrationOption:checked").val() == 0) {
    $(".genderIntegrationsDescription").hide("slow");
    $("#activity\\.genderIntegrationsDescription").text("");
  } else {
    $(".genderIntegrationsDescription").show("slow");
  }
}

/**
 * Attach to the date fields the datepicker plugin
 * 
 */
function datePickerConfig() {
  var defaultMinDateValue = $("#minDateValue").val();
  var defaultMaxDateValue = $("#maxDateValue").val();
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;

  // Start date calendar
  maxDateValue = $("#activity\\.endDate").val();
  
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.startDate").attr('readonly', true);
  var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue; 
  $("#activity\\.startDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : defaultMinDateValue,
    maxDate : finalMaxDate,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      if(selectedDate != ""){
        $("#activity\\.endDate").datepicker("option", "minDate", selectedDate);
      }
    }
  });

  // End date calendar
  minDateValue = $("#activity\\.startDate").val();
  
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.endDate").attr('readonly', true);
  var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
  $("#activity\\.endDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : finalMinDate,
    maxDate : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      if(selectedDate != ""){
        $("#activity\\.startDate").datepicker("option", "maxDate", selectedDate);
      }
    }
  });
}
