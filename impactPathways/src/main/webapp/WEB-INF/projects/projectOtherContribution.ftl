[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty", "cytoscape", "qtip","cytoscapePanzoom", "select2"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/projects/projectIpOtherContributions.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "outcomes" /]
[#assign currentSubStage = "otherContributions" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projectOtherContributions", "nameSpace":"${currentSection}/projects", "action":"otherContributions", "param":"projectID=${project.id}"}
]/]

[#assign params = {
  "otherContributions": {"id":"otherContributionsName", "name":"project.otherContributions"}
  }
/] 

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  [#if reportingCycle]
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" /><p>[@s.text name="${currentSection}.projectOtherContributions.help" /]</p>
  </div>
  [/#if]

  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  
  [@s.form action="otherContributions" cssClass="pure-form"]  
  <article class="halfContent" id=""> 
    [#include "/WEB-INF/projects/dataSheet.ftl" /]
    [#include "/WEB-INF/projects/projectIP-sub-menu.ftl" /]
    [#-- Informing user that he/she doesnt have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if submission?has_content]
      <p class="projectSubmitted">[@s.text name="submit.projectSubmitted" ][@s.param]${(submission.dateTime?date)?string.full}[/@s.param][/@s.text]</p>
    [#elseif !canEdit ]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.text name="planning.project" /]: ${project.composedId} - [@s.param][@s.text name="planning.impactPathways.otherContributions.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <div id="otherContributions" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [#else]
        [#if canEdit && !newProject]
          <div class="viewButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][/@s.url]">[@s.text name="form.buttons.unedit" /]</a></div>
        [/#if]
      [/#if]  
      [#-- Tilte --]
      <h1 class="contentTitle">[@s.text name="planning.impactPathways.otherContributions.title" /] </h1> 
      
      [#-- How are contributing to other CCAFS IP --]
      <div class="fullBlock">
        [@customForm.textArea name="project.ipOtherContribution.contribution" className="contribution limitWords-100" i18nkey="planning.impactPathways.otherContributions.contribution" editable=editable && action.hasProjectPermission("contribution", project.id) /]  
      </div>
      
      [#-- -- -- REPORTING BLOCK -- -- --]
      [#-- Others impact pathways contributions --]
      [#if reportingCycle]
        <div id="otherContributionsBlock">
          [#if project.otherContributions?has_content]
            [#list project.otherContributions as element]
              [@otherContribution index=element_index /] 
            [/#list]
          [#else]
            <div class="emptyMessage simpleBox center"><p>There is not other contributions added</p></div>
          [/#if]
        </div>
        [#if editable]<div id="addOtherContribution"><a href="" class="addLink">[@s.text name="reporting.projectOtherContributions.addOtherContribution"/]</a></div>[/#if]
        <div class="clearfix"></div>
        <br />
      [/#if]
      
      [#-- Collaborating with other CRPs --]
      [#assign crpsName= "project.ipOtherContribution.crps"/]
      <div class="fullPartBlock">      
        <div class="crpContribution panel tertiary">
          <div class="panel-head">[@customForm.text name="planning.impactPathways.otherContributions.collaboratingCRPs" readText=!editable /]</div> 
          <div class="panel-body"> 
            <ul id="contributionsBlock" class="list">
            [#if project.ipOtherContribution.crpContributions?has_content]  
              [#list project.ipOtherContribution.crpContributions as crp]
                <li class="clearfix [#if !crp_has_next]last[/#if]">
                  <input class="id" type="hidden" name="project.ipOtherContribution.crpContributions[${crp_index}].crp.id" value="${crp.crp.id}" />
                  [#-- CRP Title --]
                  <div class="fullPartBlock clearfix">
                     [#if (project.ipOtherContribution.crpContributions[crp_index]?has_content)!false]
                      <span class="name crpName">${project.ipOtherContribution.crpContributions[crp_index].crp.name!}</span>
                     [/#if]
                  </div>
                  [#-- CRP Collaboration nature --]
                  [@customForm.input name="project.ipOtherContribution.crpContributions[${crp_index}].id" display=false className="crpContributionId" showTitle=false /]
                  <div class="fullPartBlock">
                    [@customForm.textArea name="project.ipOtherContribution.crpContributions[${crp_index}].natureCollaboration" className="crpCollaborationNature limitWords-50" i18nkey="planning.impactPathways.otherContributions.collaborationNature" required=true editable=editable /]  
                  </div>
                  
                  [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
                </li>
              [/#list] 
            [#else]
              <p class="emptyText"> [@s.text name="planning.impactPathways.otherContributions.crpsEmpty" /] </p>  
            [/#if]  
            </ul>
            [#if editable]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="crps" keyFieldName="id"  displayFieldName="name" className="crpsSelect" value="" /]
            [/#if] 
          </div>
        </div> 
      </div>
      
    </div> <!-- End otherContributions -->
    
    
    [#if project.ipOtherContribution?has_content]
      <input name="project.ipOtherContribution.id" type="hidden" value="${project.ipOtherContribution.id}"/>
    [/#if]
    [#if editable] 
      <input type="hidden" id="crpsName" value="project.ipOtherContribution.crpContributions"/>
      [#-- Project identifier --]
      <input id="projectID" name="projectID" type="hidden" value="${project.id?c}" />
      <div class="" >
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

[#-- CRPs Template --]
<ul style="display:none">
  <li id="crpTemplate" class="clearfix">
    <input class="id" type="hidden" name="" value="" />
    [#-- CRP Title --]
    <div class="fullPartBlock clearfix">
      <span class="name crpName"></span>
    </div>
    [#-- CRP Collaboration nature --]
    <div class="fullPartBlock">
      [@customForm.textArea name="" className="crpCollaborationNature limitWords-50" i18nkey="planning.impactPathways.otherContributions.collaborationNature" editable=editable required=true/]  
    </div>
    [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
  </li>
</ul>

[#-- Internal parameters --]
[#list params?keys as prop]<input id="${params[prop].id}" type="hidden" value="${params[prop].name}" />[/#list]

[#-- Other contribution template --]
[@otherContribution template=true /]

[#macro otherContribution index="0" template=false]
  [#assign customName = "${params.otherContributions.name}[${template?string('-1',index)}]" /]
  [#assign contribution = (customName?eval)! /]
  <div id="otherContribution-${template?string('template',index)}" class="otherContribution simpleBox" style="display:${template?string('none','block')}">
    <div class="loading" style="display:none"></div>
    [#-- Edit/Back/remove buttons --]
    [#if (editable && canEdit)]<div class="removeElement" title="[@s.text name="reporting.projectOtherContributions.removeOtherContribution" /]"></div>[/#if]
    [#-- Other Contribution ID --]
    <input type="hidden" name="${customName}.id" class="otherContributionId" value="${(contribution.id)!-1}"/>
    <div class="fullBlock">
      [#-- Region --]
      <div class="halfPartBlock">
        [@customForm.select name="${customName}.region" className="otherContributionRegion" label="" i18nkey="reporting.projectOtherContributions.region" listName="regions"  required=true editable=editable && action.hasProjectPermission("otherContributionIndicator", project.id) /]
      </div> 
    </div>
    [#-- Indicator --]
    <div class="fullBlock">
      [@customForm.select name="${customName}.indicators" className="otherContributionIndicator" label="" i18nkey="reporting.projectOtherContributions.indicators" listName="otherIndicators" required=true editable=editable && action.hasProjectPermission("otherContributionIndicator", project.id) /]
    </div>
    [#-- Describe how you are contributing to the selected outcome --]
    <div class="fullBlock">
      <h6>[@customForm.text name="reporting.projectOtherContributions.description" param="${currentReportingYear}" readText=!editable /]:[@customForm.req required=editable && action.hasProjectPermission("otherContributionDescription", project.id) /]</h6>
      [@customForm.textArea name="${customName}.description" className="otherContributionDescription limitWords-100"  i18nkey="" showTitle=false required=true editable=editable && action.hasProjectPermission("otherContributionDescription", project.id) /]
    </div>
    [#-- Target contribution --]
    <div class="fullBlock">
      <h6>[@customForm.text name="reporting.projectOtherContributions.target" readText=!editable /]:</h6>
      <div class="halfPartBlock">
        [@customForm.input name="${customName}.target" className="otherContributionTarget" i18nkey="" showTitle=false editable=editable && action.hasProjectPermission("otherContributionTarget", project.id) /]
      </div>
    </div>
  </div> 
[/#macro]


[#include "/WEB-INF/global/pages/footer.ftl"]