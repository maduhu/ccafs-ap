[#ftl]
<div id="arrowDown">
  <img src="${baseUrl}/images/global/complete-arrow.png" />
</div>
<nav id="secondaryMenu">  
  <ul>
    <a [#if currentReportingSection == "activities"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/activities.do"><li>Activities</li></a>
    <a [#if currentReportingSection == "outputs"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/outputSummary.do"><li>Summary by outputs</li></a>
    <a [#if currentReportingSection == "publications"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/publications.do"><li>Publications</li></a>
    <a [#if currentReportingSection == "caseStudies"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/caseStudies.do"><li>Case studies</li></a>
    <a [#if currentReportingSection == "outcomes"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/outcomes.do"><li>Outcomes</li></a>
    <!-- a [#if currentReportingSection == "leverage"] class="currentReportingSection" [/#if] href=""><li>Leverage</li></a -->
    [#if userRole == "TL" || userRole == "Admin"]
      <a [#if currentReportingSection == "tlRpl"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/tlOutputs.do"><li>TL/RPL only</li></a>
    [/#if]
    
    [#if userRole == "RPL"]
      <a [#if currentReportingSection == "tlRpl"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/rplSynthesis.do"><li>TL/RPL only</li></a>
    [/#if]
  </ul>
</nav>