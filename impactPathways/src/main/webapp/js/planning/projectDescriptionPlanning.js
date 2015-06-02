//Limits for textarea input
var lWordsElemetTitle = 50;
var lWordsElemetDesc = 300; 

$(document).ready(function(){
  var $uploadWp= $('#uploadWorkPlan .checkbox input'); 
  var $uploadWpCont= $('#uploadWorkPlan .uploadContainer');
  
  datePickerConfig({
    "startDate" : "#project\\.startDate",
    "endDate" : "#project\\.endDate",
    defaultMinDateValue : $("#minDateValue").val(),
    defaultMaxDateValue : $("#maxDateValue").val()
  });
  setProgramId();
  addChosen();
  applyWordCounter($("textarea.project-title"), lWordsElemetTitle);
  applyWordCounter($("textarea.project-description"), lWordsElemetDesc);
  
  
  
  $uploadWp.on('change', toggleUploadInputs); 
  if(getUploadWpState())$uploadWpCont.show();else $uploadWpCont.hide();
  
  $("#projectCoreProjects .isLinked input").on('click', checkBilateralProjectType);
  
  function checkBilateralProjectType(e) {
    var $coreProjects= $("#projectCoreProjects .coreProjects");
    if($(this).val()==1)
      $coreProjects.fadeIn();
    else
      $coreProjects.fadeOut();
  } 
  
  function toggleUploadInputs(){ 
    $uploadWpCont.slideToggle(getUploadWpState()); 
  }
  function getUploadWpState(){
    return $uploadWp.is(':checked');
  }
});

/**
 * Attach to the date fields the datepicker plugin
 * 
 */
function datePickerConfig(element){
  var defaultMinDateValue = element.defaultMinDateValue;
  var defaultMaxDateValue = element.defaultMaxDateValue;
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;
  
  // Start date calendar
  maxDateValue = $(element.endDate).val();
  
  // Add readonly attribute to prevent inappropriate user input
  // $(element.startDate).attr('readonly', true);
  var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue;
  $(element.startDate).datepicker({
    dateFormat : "yy-mm-dd",
    minDate : defaultMinDateValue,
    maxDate : finalMaxDate,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate){
      if (selectedDate != "") {
        $(element.endDate).datepicker("option", "minDate", selectedDate);
      }
    }
  });
  
  // End date calendar
  minDateValue = $(element.startDate).val();
  
  // Add readonly attribute to prevent inappropriate user input
  // $(element.endDate).attr('readonly', true);
  var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
  $(element.endDate).datepicker({
    dateFormat : "yy-mm-dd",
    minDate : finalMinDate,
    maxDate : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate){
      if (selectedDate != "") {
        $(element.startDate).datepicker("option", "maxDate", selectedDate);
      }
    }
  });
}

// Activate the chosen plugin.
function addChosen(){
  $("form select").chosen({
    search_contains : true
  });
  
}

// Set default Program ID
function setProgramId(){
  var programId = $("input#programID").val();
  $("input[value='" + programId + "'][name$='regions'], input[value='" + programId + "'][name$='flagships']").attr("checked", true).attr("disabled", true);
}

