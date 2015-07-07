[#ftl]
[#assign title = "Impact Pathways - Other Contribution" /]
[#assign globalLibs = ["jquery", "noty", "cytoscape", "qtip","cytoscapePanzoom", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js", "${baseUrl}/js/global/ipGraph.js", "${baseUrl}/js/planning/projectIpOtherContributions.js"] /]
[#assign currentSection = "planning" /]
[#assign currentPlanningSection = "projects" /]
[#assign currentStage = "outcomes" /]
[#assign currentSubStage = "otherContributions" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projectOutcomes", "nameSpace":"planning/projects", "action":"outcomes", "param":"projectID=${project.id}"},
  {"label":"projectOtherContributions", "nameSpace":"planning/projects", "action":"otherContributions", "param":"projectID=${project.id}"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
[#import "/WEB-INF/global/macros/logHistory.ftl" as log/]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p> [@s.text name="planning.impactPathways.otherContributions.help" /] </p>
  </div>
  [#include "/WEB-INF/planning/planningProjectsSubMenu.ftl" /]
  
  [@s.form action="ipOtherContributions" cssClass="pure-form"]  
  <article class="halfContent" id=""> 
    [#include "/WEB-INF/planning/planningDataSheet.ftl" /]
    [#include "/WEB-INF/planning/projectIP-planning-sub-menu.ftl" /]
    [#-- Informing user that he/she doesnt have enough privileges to edit. See GrantActivityPlanningAccessInterceptor--]
    [#if !canEdit]
      <p class="readPrivileges">
        [@s.text name="saving.read.privileges"]
          [@s.text name="planning.project" /]: ${project.composedId} - [@s.param][@s.text name="planning.impactPathways.otherContributions.title"/][/@s.param]
        [/@s.text]
      </p>
    [/#if]
    
    <div id="otherContributions" class="borderBox">
      [#-- Button for edit this section --]
      [#if (!editable && canEdit)]
        <div class="editButton"><a href="[@s.url includeParams='get'][@s.param name="edit"]true[/@s.param][/@s.url]">[@s.text name="form.buttons.edit" /]</a></div>
      [/#if]
      [#-- Tilte --]
      <h1 class="contentTitle">[@s.text name="planning.impactPathways.otherContributions.title" /] </h1> 
      
      [#-- How are contributing to other CCAFS IP --]
      <div class="fullPartBlock">
        [@customForm.textArea name="project.ipOtherContribution.contribution" i18nkey="planning.impactPathways.otherContributions.contribution" editable=editable /]  
      </div>
      [#-- Contribution to another center activity --]
      <div class="fullPartBlock">
        [@customForm.textArea name="project.ipOtherContribution.additionalContribution" i18nkey="planning.impactPathways.otherContributions.additionalcontribution" editable=editable /]  
      </div>
      
      [#-- Collaborating with other CRPs --]
      [#assign crpsName= "project.ipOtherContribution.crps"/]
      <div class="fullPartBlock">      
        <div class="crpContribution panel tertiary">
          <div class="panel-head">[@customForm.text name="planning.impactPathways.otherContributions.collaboratingCRPs" readText=!editable /]</div> 
          <div class="panel-body"> 
            <ul class="list">  
              [#assign list = [
                {"id":"1", "name":"Agriculture for Nutrition and Health"},
                {"id":"2", "name":"Aquatic Agricultural Systems"}
              ] /]
              [#list list as crp]
                <li class="clearfix [#if !crp_has_next]last[/#if]">
                  <input class="id" type="hidden" name=crpsName value="${crp.id}" />
                  <span class="name">${crp.name}</span> 
                  [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
                </li>
              [/#list] 
            </ul>
            [#if editable]
              [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="crps" keyFieldName="id"  displayFieldName="name" className="crpsSelect" value="" /]
            [/#if] 
          </div>
        </div> 
      </div>
      
      [#-- Lessons regarding other contributions and possible implications --]
      <div class="fullPartBlock">
        [@customForm.textArea name="project.ipOtherContribution.lessonsAndImplications" i18nkey="planning.impactPathways.otherContributions.lessonsAndImplications" editable=editable /]  
      </div>
      
    </div> <!-- End otherContributions -->
    
    [#if project.ipOtherContribution?has_content]
      <input name="project.ipOtherContribution.id" type="hidden" value="${project.ipOtherContribution.id}"/>
    [/#if]
    [#if editable] 
      <input type="hidden" id="crpsName" value="${crpsName}"/>
      [#-- Project identifier --]
      <input name="projectID" type="hidden" value="${project.id?c}" />
      <div class="borderBox">
        [@customForm.textArea name="justification" i18nkey="saving.justification" required=true className="justification"/]
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
  
  [#-- CRPs Template --]
  <ul style="display:none">
    <li id="crpTemplate" class="clearfix">
      <input class="id" type="hidden" name="" value="" />
      <span class="name"></span> 
      [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if] 
    </li>
  </ul>
</section>
[#include "/WEB-INF/global/pages/footer.ftl"]