[#ftl]
[#assign title = "Project Outcomes" /]
[#assign globalLibs = ["jquery", "noty", "chosen", "cytoscape", "qtip","cytoscapePanzoom"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectImpactPathway.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "outcomes" /] 
[#assign currentSubStage = "outcomes" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"pOutcomes", "nameSpace":"${currentSection}/projects", "action":"outcomes", "param":"projectID=${project.id}"}
]/]

[#assign years= [midOutcomeYear, currentPlanningYear, currentPlanningYear+1] /]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>
    [@s.text name="${currentSection}.projectImpactPathways.help" ]
      [@s.param]<a href= [@s.url namespace="/" action='glossary'][/@s.url]>[@s.text name="planning.projectImpactPathways.help.glossary" /]</a>[/@s.param]
    [/@s.text]
    </p>
  </div>

  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]

  [@s.form action="outcomes" cssClass="pure-form" enctype="multipart/form-data" ]  
  <article class="halfContent" id="activityImpactPathway">
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    [#-- Informing user that he/she doesn't have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"][@s.param][@s.text name=title/][/@s.param][/@s.text]
      </p>
    [/#if]
    [#if project.startDate??]
    <div id="projectOutcomes" class="borderBox">
      [#if !editable && canEdit]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
        <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      [#assign canEditStatement = action.hasProjectPermission("statement", project.id) /]
      [#-- Project  outcome block --]
      <div class="fullPartBlock clearfix">
        <h1 class="contentTitle">[@s.text name="planning.projectOutcome.narrative" /] </h1> 
        [#-- Project Outcome statement --]
        <div class="fullPartBlock" id="projectOutcomeStatement">
          [@customForm.textArea name="project.outcomes[${midOutcomeYear}].statement" required=!project.bilateralProject className="limitWords-150" i18nkey="planning.projectOutcome.statement" editable=(editable && canEditStatement) /]
        </div>
        [#-- Annual progress --]
        [#list project.startDate?string.yyyy?number..midOutcomeYear?number-1 as year]
          [#assign yearEditable = editable && (year gte currentPlanningYear?number) && canEditStatement /]
          [#assign yearRequired = !project.bilateralProject && ((year == currentPlanningYear) || (year == currentPlanningYear+1)) /]
          <div class="fullPartBlock">
            <h6>[@customForm.text name="planning.projectOutcome.annualProgress" readText=!editable param="${year}" /]:[@customForm.req required=yearRequired && editable /]</h6>
            [@customForm.textArea name="project.outcomes[${year?string}].statement" required=yearRequired && editable className="limitWords-150" showTitle=false editable=yearEditable /]
          </div>
          [#-- -- -- REPORTING BLOCK -- -- --]
          [#if reportingCycle && (year == currentReportingYear) ]
          <div class="fullPartBlock">
            <h6>[@customForm.text name="reporting.projectOutcomes.annualProgressCurrentReporting" readText=!editable param="${year}" /]:[@customForm.req required=editable /]</h6>
            [@customForm.textArea name="project.outcomes[${year?string}].anualProgress" required=editable className="limitWords-300" showTitle=false editable=editable && action.hasProjectPermission("annualProgress", project.id) /]
          </div>
          
          [#-- Comunication and engagement activities --]
          <div class="fullPartBlock">
            [@customForm.textArea name="project.outcomes[${year?string}].comunication" className="limitWords-100" i18nkey="reporting.projectOutcomes.commEngagementOutcomes" required=true editable=editable && action.hasProjectPermission("communicationEngagement", project.id) /]
          </div>
          
          [#-- Upload summary--]
          <div class="fullPartBlock fileUpload uploadSummary">
            <h6>[@customForm.text name="reporting.projectOutcomes.uploadSummary" readText=!editable /]:</h6>
            <div class="uploadContainer" title="[@s.text name="reporting.projectOutcomes.uploadSummary.help" /]">
              [#if (action.getOutcomeFile(year)?has_content)!false]
                [#if editable]<span id="remove-file" class="remove"></span>[#else]<span class="file"></span>[/#if] 
                <p><a href="${ProjectOutcomeURL}${(action.outcomeFile(year))!}">${(action.getOutcomeFile(year))!}</a></p>
              [#else]
                [#if editable && action.hasProjectPermission("uploadSummary", project.id)]
                  [@customForm.inputFile name="file"  /]
                [#else]  
                  [@s.text name="form.values.notFileUploaded" /]
                [/#if] 
              [/#if]
            </div>  
          </div>
          
          [/#if]
        [/#list]
        <input name="project.outcome[midOutcomeYear].id" type="hidden" value="${project.outcomes[midOutcomeYear+""].id?c}" />
      </div>
    </div>
    [#else]
      <p class="simpleBox center">[@s.text name="planning.projectOutcome.message.dateUndefined" /]</p>
    [/#if]
    [#if !newProject]
    <div id="lessons" class="borderBox">
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]#lessons">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]
      [#-- Lessons learnt from last planning/reporting cycle --]
      [#if (projectLessonsPreview.lessons?has_content)!false]
      <div class="fullBlock">
        <h6>[@customForm.text name="${currentSection}.projectOutcomes.previousLessons" param="${reportingCycle?string(currentReportingYear,currentPlanningYear-1)}" /]:</h6>
        <div class="textArea "><p>${projectLessonsPreview.lessons}</p></div>
      </div>
      [/#if]
      [#-- Planning/Reporting lessons --]
      <div class="fullBlock">
        <input type="hidden" name="projectLessons.id" value=${(projectLessons.id)!"-1"} />
        <input type="hidden" name="projectLessons.year" value=${reportingCycle?string(currentReportingYear,currentPlanningYear)} />
        <input type="hidden" name="projectLessons.componentName" value="${actionName}">
        [@customForm.textArea name="projectLessons.lessons" i18nkey="${currentSection}.projectOutcomes.lessons" editable=editable /]
      </div>
    </div>
    [/#if]
    
    [#if editable] 
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="[#if !newProject && !reportingCycle]borderBox[/#if]" >
        [#if !newProject && !reportingCycle] [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/][/#if]
        <div class="buttons">
          [@s.submit type="button" name="save"][@s.text name="form.buttons.save" /][/@s.submit]
          [@s.submit type="button" name="next"][@s.text name="form.buttons.next" /][/@s.submit]
          [@s.submit type="button" name="cancel"][@s.text name="form.buttons.cancel" /][/@s.submit]
        </div>
      </div>
    [#else]
        [#-- Display Log History --]
        [#if history??][@log.logList list=history /][/#if]
    [/#if]
    
  </article>
  [/@s.form]  
</section> 

[#-- File template --]
[@customForm.inputFile name="file" template=true /] 

[#include "/WEB-INF/global/pages/footer.ftl"]
