[#ftl]
<div id="arrowDown">
  
</div>
<nav id="secondaryMenu">  
  <ul>
    <a [#if currentReportingSection == "activities"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/activities.do"><li>[@s.text name="menu.secondary.reporting.activities" /]</li></a>
    <a [#if currentReportingSection == "outputs"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/outputSummary.do"><li>[@s.text name="menu.secondary.reporting.outputSummaries" /]</li></a>
    <a [#if currentReportingSection == "communications"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/communications.do"><li>[@s.text name="menu.secondary.reporting.communications" /]</li></a>
    <a [#if currentReportingSection == "caseStudies"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/caseStudies.do"><li>[@s.text name="menu.secondary.reporting.caseStudies" /]</li></a>
    <a [#if currentReportingSection == "outcomes"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/outcomes.do"><li>[@s.text name="menu.secondary.reporting.outcomes" /]</li></a>
    <a [#if currentReportingSection == "outcomeIndicators"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/themeOneOutcomeIndicators.do"><li>[@s.text name="menu.secondary.reporting.outcomeIndicators" /]</li></a>
    <a [#if currentReportingSection == "indicators"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/indicators.do"><li>[@s.text name="menu.secondary.reporting.indicators" /]</li></a>
    <a [#if currentReportingSection == "leverages"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/leverages.do"><li>[@s.text name="menu.secondary.reporting.leverages" /]</li></a>
    <!-- a [#if currentReportingSection == "leverage"] class="currentReportingSection" [/#if] href=""><li>Leverage</li></a -->
    [#if currentUser.isTL() || currentUser.isAdmin()]
      <a [#if currentReportingSection == "tlRpl"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/tlOutputs.do"><li>[@s.text name="menu.secondary.reporting.tlRplOnly" /]</li></a>
    [/#if]
    
    [#if currentUser.isRPL()]
      <a [#if currentReportingSection == "tlRpl"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/rplSynthesis.do"><li>[@s.text name="menu.secondary.reporting.tlRplOnly" /]</li></a>
    [/#if]
  </ul>
  
  [#if !currentUser.isPI() ]
    [#if canSubmit ]
      <div id="submitButtonBlock" class="buttons">
        [@s.form action="submit" id="submitForm" ]
          [@s.submit type="button" name="save" method="submit" cssClass="test" ][@s.text name="form.buttons.submit" /][/@s.submit] 
        [/@s.form]  
      </div>
    [#else]
      <div id="submitButtonBlock" class="buttons">
        <img src="${baseUrl}/images/global/icon-complete.png" /> [@s.text name="submit.submitted" /]
      </div>
    [/#if]
  [/#if]
  
</nav>