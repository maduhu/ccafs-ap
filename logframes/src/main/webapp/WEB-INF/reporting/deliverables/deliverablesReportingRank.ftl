[#ftl]
[#assign title = "Activity deliverables ranking Report" /]
[#assign globalLibs = ["jquery", "noty", "star-rating"] /]
[#assign customJS = ["${baseUrl}/js/reporting/deliverables/deliverablesReportingRank.js"] /]
[#assign currentSection = "reporting" /]
[#assign currentReportingSection = "activities" /]
[#assign currentStage = "ranking" /]
[#assign userRole = "${currentUser.role}"]

[#include "/WEB-INF/global/pages/header.ftl" /]
[#include "/WEB-INF/global/pages/main-menu.ftl" /]
[#import "/WEB-INF/global/macros/forms.ftl" as customForm /]

<section class="content">
  <div class="helpMessage">
    <img src="${baseUrl}/images/global/icon-help.png" />
    <p>[@s.text name="reporting.activityDeliverables.ranking.help" /]</p>
  </div>
  [#include "/WEB-INF/global/pages/reporting-secondary-menu.ftl" /]
  
  [@s.form action="deliverablesRank"]
  <article class="halfContent">
    [#include "/WEB-INF/reporting/deliverables/deliverablesReportingSubMenu.ftl" /]
    <h1 class="contentTitle">
      [@s.text name="reporting.activityDeliverables.deliverable" /] - ${deliverableID}
    </h1> 
    
    <div id="trafficLightQuestions" class="fullBlock">
      <h6>[@s.text name="reporting.activityDeliverables.trafficLight" /]</h6>
      <table class="borderBox">
        <tbody>
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.metadataDocumented" /]</td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.metadataDocumented" list="yesNoRadio" /]</td>
          </tr>
          
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.collectionTools" /]<span class="quote"> [@s.text name="reporting.activityDeliverables.trafficLight.collectionTools.quote" /]<span> </td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.haveCollectionTools" list="yesNoRadio" /]</td>
          </tr>
          
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.qualityDocumented" /]</td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.qualityDocumented" list="yesNoRadio" /]</td>
          </tr>
          
          <tr>
            <td class="question"> [@s.text name="reporting.activityDeliverables.trafficLight.supportDissemination" /]</td>
            <td class="answer"> [@s.radio name="deliverable.trafficLight.supportingDissemination" list="yesNoRadio" /]</td>
          </tr>
          
        </tbody>  
      </table> 
    </div>
    
    <h6>[@s.text name="reporting.activityDeliverables.ranking.rankDeliverable" /]</h6>
    <div class="borderBox">
      <p>[@s.text name="reporting.activityDeliverables.ranking.rankDeliverableText" /]</p>
      <div id="rankingBlock" style="text-align:center;"> 
        [#assign score = deliverable.getScoreByLeader(activityLeaderID) /]
        <input class="hover-star required" type="radio" name="deliverable.scores[${activityLeaderID}]" value="1" [#if score == 1] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /]"/>
        <input class="hover-star" type="radio" name="deliverable.scores[${activityLeaderID}]" value="2" [#if score == 2] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.important' /]"/>
        <input class="hover-star" type="radio" name="deliverable.scores[${activityLeaderID}]" value="3" [#if score == 3] checked [/#if] title="[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /]" />
        <div id="hover-test" style=""></div> 
        <div class="clearfix"></div>
      </div>
     
    </div>
  
    <div id="rate-legend">
      <ul>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.veryImportant' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.veryImportant.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.important' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.important.help' /] </li>
        <li><strong>[@s.text name='reporting.activityDeliverables.ranking.level.notImportant' /] </strong> -
        [@s.text name='reporting.activityDeliverables.ranking.level.notImportant.help' /] </li>
      </ul>
    </div>
   
    
    <!-- internal parameter -->
    <input name="activityID" type="hidden" value="${activityID}" />
    <input name="deliverableID" type="hidden" value="${deliverable.id?c}" />
    
    [#if canSubmit]
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