[#ftl]
[#assign title = "Main information" /]
[#assign globalLibs = ["jquery", "noty", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/planning/mainInformation.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "mainInformation" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.mainInformation.help" /] </p>
  </div>
  [#include "/WEB-INF/global/pages/planning-secondary-menu.ftl" /]
  
  [@s.form action="mainInformation"]  
  <article class="halfContent" id="mainInformation">
    <h1 class="contentTitle">
      ${activity.leader.acronym}: 
      [#if activity.commissioned] 
        [@s.text name="planning.mainInformation.commissionedActivity" /] ${activity.activityId}
      [#else] 
        [@s.text name="planning.mainInformation.activity" /] ${activity.activityId}
      [/#if]
    </h1>
    
    [#-- Activity identifier --]
    <input name="activityID" value="${activity.id?c}" type="hidden"/>
    [#-- Budget identifier --]
    <input name="activity.budget.id" value="${activity.budget.id?c}" type="hidden"/>
    [#-- Budget 'No funds' text --]
    <input id="activity.budget.noFunds" value="[@s.text name="planning.mainInformation.budget.noFunds" /] " type="hidden"/>
    
    
    [#-- Hidden values used by js --]
    <input id="minDateValue" value="${startYear?c}-01-01" type="hidden"/>
    <input id="maxDateValue" value="${endYear?c}-12-31" type="hidden"/>
    
    [#-- Commissioned and continuous --]
    [#if activity.continuousActivity?has_content]
      <div class="fullblock">
        <div class="halfPartBlock continuation">
          [#if activity.continuousActivity?has_content]
            [@s.text name="planning.mainInformation.continuationActivity" /] 
            <a href="[@s.url action='activity' namespace="/"][@s.param name='${publicActivtyRequestParameter}']${activity.continuousActivity.id}[/@s.param][/@s.url]" target="_blank"> 
              [@s.text name="planning.mainInformation.activity" /] ${activity.continuousActivity.activityId} 
            </a>
          [/#if]
        </div>
      </div>
    [/#if]
    
    [#-- Title --]
    <div class="fullBlock">
      [@customForm.textArea name="activity.title"  i18nkey="planning.mainInformation.title" required=true /]
    </div>
    
    [#-- Description --]
    <div class="fullBlock">
      [@customForm.textArea name="activity.description" i18nkey="planning.mainInformation.descripition" required=true /]
    </div>
    
    [#-- Milestones --]
    <div class="halfPartBlock">
      [@customForm.select name="activity.milestone" label="" i18nkey="planning.mainInformation.milestone" listName="milestones" keyFieldName="id"  displayFieldName="code" value="${activity.milestone.id?c}" className="milestones" required=true /]
    </div>
    
    [#-- Logframe link --]
    <div class="halfPartBlock">
      [#if currentYear == 2013]
        <a href="../resources/documents/Logframe_2013-2015.pdf" target="_blank">view logframe </a> 
      [#elseif currentYear == 2014]
        <a href="../resources/documents/Logframe_2014-2015.pdf" target="_blank">view logframe </a> 
      [#else]
        <a href="../resources/documents/Logframe_2013-2015.pdf" target="_blank">view logframe </a> 
      [/#if]
    </div>
    
    [#-- Budget --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.budget.usd" type="text" i18nkey="planning.mainInformation.budget" required=true /]
    </div>
    
    [#-- Budget Percentages --]
    <div class="halfPartBlock">
      <div class="halfPartBlock">
        [@customForm.select name="activity.budget.cgFund" label="" i18nkey="planning.mainInformation.budget.cgfunds" listName="budgetPercentages" keyFieldName="id"  displayFieldName="percentage" value="activity.budget.cgFund.id" /]              
      </div>
      <div class="halfPartBlock">
        [@customForm.select name="activity.budget.bilateral" label="" i18nkey="planning.mainInformation.budget.bilateral" listName="budgetPercentages" keyFieldName="id"  displayFieldName="percentage" value="activity.budget.bilateral.id" /]
      </div>
    </div>
    
    [#-- Start Date --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.startDate" type="text" i18nkey="planning.mainInformation.startDate" required=true /]
    </div>
    
    [#-- End Date --]
    <div class="halfPartBlock">
      [@customForm.input name="activity.endDate" type="text" i18nkey="planning.mainInformation.endDate" required=true /]
    </div>
    
    [#-- Gender integration --]
    <div id="gender">
      <div class="fullBlock">
        [@customForm.radioButtonGroup i18nkey="planning.mainInformation.genderIntegration" label="" name="genderIntegrationOption" listName="genderOptions" value="${hasGender?string('1', '0')}" /]
      </div>
      <div class="fullBlock genderIntegrationsDescription">
        [@customForm.textArea name="activity.genderIntegrationsDescription" i18nkey="planning.mainInformation.genderIntegrationDescription" required=true /]
      </div>
    </div>
        
    <fieldset class="fullBlock">
        <legend>Contact persons</legend>
        <div id="contactPersonBlock">
          [#if activity.contactPersons?has_content]
            [#-- If there is contact persons show the list --]
            [#list activity.contactPersons as contactPerson]
              
              <div id="contactPerson-${contactPerson_index}" class="contactPerson">
                [#-- Contact person id--]
                <input type="hidden" name="activity.contactPersons[${contactPerson_index}].id" value="${contactPerson.id?c}">
                
                [#-- Contact name --]
                <div class="halfPartBlock">
                  [@customForm.input name="activity.contactPersons[${contactPerson_index}].name" type="text" i18nkey="planning.mainInformation.contactName" required=true /]
                </div>
                
                [#-- Contact email --]
                <div class="halfPartBlock">
                  [@customForm.input name="activity.contactPersons[${contactPerson_index}].email" type="text" i18nkey="planning.mainInformation.contactEmail" required=true /]
                </div>
        
                [#-- Adding remove image --]
                <a href="#" >
                  <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
                </a>
                
              </div>
            [/#list]
          [#else]
            <div class="contactPerson">
              [#-- Contact person id--]
              <input type="hidden" name="activity.contactPersons[0].id" value="-1">
              
              [#-- Contact person --]
              <div class="halfPartBlock">
                [@customForm.input name="activity.contactPersons[0].name" type="text" i18nkey="planning.mainInformation.contactName" value="" /]
              </div>
              
              [#-- Contact email --]
              <div class="halfPartBlock">
                [@customForm.input name="activity.contactPersons[0].email" type="text" i18nkey="planning.mainInformation.contactEmail" value="" /]
              </div>
              
              [#-- Adding remove image --]
              <a href="#" >
                <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
              </a>
            </div>
          [/#if]
        </div>
    
    <div id="addContactPerson" class="addLink">
      <img src="${baseUrl}/images/global/icon-add.png" />
      <a href="" class="addContactPerson" >[@s.text name="planning.mainInformation.addContactPerson" /]</a>
    </div>
    </fieldset>
    
    
    [#-- Contact person template --]
    <div id="contactPersonTemplate" style="display:none;">
      [#-- Contact person id--]
      <input type="hidden" name="id" value="-1">
      
      [#-- Contact name --]
      <div class="halfPartBlock">
        [@customForm.input name="name" type="text" i18nkey="planning.mainInformation.contactName" /]
      </div>
      
      [#-- Contact email --]
      <div class="halfPartBlock">
        [@customForm.input name="email" type="text" i18nkey="planning.mainInformation.contactEmail" /]
      </div>

      [#-- Adding remove image --]
      <a href="#" >
        <img src="${baseUrl}/images/global/icon-remove.png" class="removeContactPerson" />
      </a>
      
    </div>
    
    [#-- Only the owner of the activity can see the action buttons --]
    [#if activity.leader.id == currentUser.leader.id && canSubmit]
      <div class="buttons">
        [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
        [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
        [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
      </div>
    [/#if]
  </article>
  [/@s.form]  
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]