[#ftl]
[#assign title = "Projects" /]
[#assign globalLibs = ["jquery", "dataTable", "noty","autoSave"] /]
[#assign customJS = ["${baseUrl}/js/planning/projectsListPlanning.js"] /]
[#assign customCSS = ["${baseUrl}/css/libs/dataTables/jquery.dataTables-1.9.4.css", "${baseUrl}/css/global/customDataTable.css"] /]
[#assign currentSection = "planning" /]

[#assign breadCrumb = [
  {"label":"planning", "nameSpace":"planning", "action":"projectsList"},
  {"label":"projects", "nameSpace":"planning", "action":""}
]/]


[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]
[#import "/WEB-INF/global/templates/projectsListTemplate.ftl" as projectList /]
    
<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="planning.projectsList.help1" /]</p>
  </div>
  <article class="fullBlock" id="mainInformation">
    [#if projects?size>0]
      [#-- Projects List (My Projects) --]
      <h3 class="projectSubTitle">[@s.text name="preplanning.projects.yourProjects"/]</h3>
      <div class="loadingBlock"></div>
      <div style="display:none">
        [@projectList.projectsList projects=projects canValidate=true namespace="/planning/projects" /]
      </div>
    [#else]
      <div class="borderBox center">
        [#if canEdit]
          <p>[@s.text name="planning.projects.empty"][@s.param][@s.url namespace="/pre-planning" action='projects'/][/@s.param][/@s.text]</p>
        [#else]
          <p>[@s.text name="planning.projects.empty.PL" /]</p>
        [/#if]
      </div>
    [/#if]

    [#if securityContext.canAddCoreProject() || securityContext.canAddBilateralProject()]
      <div class="buttons">
        [#if securityContext.canAddCoreProject()]
          <a class="addButton" href="[@s.url namespace="/planning" action='addNewCoreProject'/]">[@s.text name="preplanning.projects.addCoreProject" /]</a>
        [/#if]
        [#if securityContext.canAddBilateralProject()]
          <a class="addButton" href="[@s.url namespace="/planning" action='addNewBilateralProject'/]">[@s.text name="preplanning.projects.addBilateralProject" /]</a>
        [/#if]
        [#if securityContext.canAddCofoundedProject()]
        [/#if]
      </div>
    [/#if]

    <div class="clearfix"></div>  
    <hr/>
    [#-- Projects List (Other Projects) --]
    <h3 class="projectSubTitle">[@s.text name="preplanning.projects.otherProjects" /]</h3>
    <div class="loadingBlock"></div>
    <div style="display:none">
      [@projectList.projectsList projects=allProjects canValidate=true namespace="/planning/projects" /]
    </div>
  </article>
</section>
[@customForm.confirmJustification action="deleteProject" namespace="/planning/projects" nameId="projectID" title="Remove Project" /]
[#include "/WEB-INF/global/pages/footer.ftl"]
