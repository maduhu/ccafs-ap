[#ftl]
[#assign title = "Project Submission" /]
[#assign globalLibs = ["jquery", "noty", "autoSave", "chosen"] /]
[#assign customJS = ["${baseUrl}/js/global/utils.js"] /]
[#assign currentSection = cycleName?lower_case /]
[#assign currentCycleSection = "projects" /]
[#assign currentStage = "submit" /]

[#assign breadCrumb = [
  {"label":"${currentSection}", "nameSpace":"${currentSection}", "action":"projectsList"},
  {"label":"projects", "nameSpace":"${currentSection}", "action":"projectsList"}
]/]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm/]
    
<section class="content">
  [#include "/WEB-INF/projects/projectsSubMenu.ftl" /]
  <article class="halfContent" id="projectSubmition">
    <h1 class="successfullyTitle">Project Submit</h1>
    <div class="borderBox">
    [#if complete]
      <h2 class="successTitle">The project has been successfully submitted</h2>
      <div class="fullPartBlock">
        <h6>Project title</h6>
        <p>${(project.title)!"Title not defined"}</p>
      </div> 
      <div class="fullPartBlock">
        <h6>Submission date</h6>
        [#if reportingCycle]
          [#assign submission = (project.isSubmitted(currentReportingYear, 'Reporting'))!/]
        [#else]
          [#assign submission = (project.isSubmitted(currentPlanningYear, 'Planning'))!/]
        [/#if]
        <p>${(submission.cycle)!} - ${(submission.year)!} - ${(submission.dateTime?date)!} by ${(submission.user.firstName)!} ${(submission.user.lastName)!}</p>
      </div> 
      <div class="fullPartBlock">
        <h6>Download Full Project Report</h6>
        <a href="[@s.url namespace="/summaries" action='project'][@s.param name='cycle']${cycleName}[/@s.param][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]" class="button-pdf-format" target="__BLANK">PDF Format</a> 
        <a href="[@s.url namespace="/summaries" action='project'][@s.param name='projectID']${project.id?c}[/@s.param][@s.param name='cycle']${submission.cycle}[/@s.param][/@s.url]" class="button-pdf-format" target="__BLANK">PDF Format</a> 
      </div> 
    [#else]
      <p>The project is still incomplete, please go to the sections without the green check mark and complete the missing fields before submitting your project.</p>
    [/#if]
    </div>
  </article>
   
</section> 


[#include "/WEB-INF/global/pages/footer.ftl"]