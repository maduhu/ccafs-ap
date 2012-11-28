[#ftl]
<nav id="secondaryMenu">
  <ul>
    <a [#if currentReportingSection == "activities"] class="currentReportingSection" [/#if] href="${baseUrl}/reporting/activities.do"><li>Activities</li></a>
    <a [#if currentReportingSection == "outputs"] class="currentReportingSection" [/#if] href=""><li>Summary by outputs</li></a>
    <a [#if currentReportingSection == "publications"] class="currentReportingSection" [/#if] href=""><li>Publications</li></a>
    <a [#if currentReportingSection == "caseStudies"] class="currentReportingSection" [/#if] href=""><li>Case studies</li></a>
    <a [#if currentReportingSection == "outcomes"] class="currentReportingSection" [/#if] href=""><li>Outcomes</li></a>
    <a [#if currentReportingSection == "leverage"] class="currentReportingSection" [/#if] href=""><li>Leverage</li></a>
    <a [#if currentReportingSection == "tlRpl"] class="currentReportingSection" [/#if] href=""><li>TL/RPL only</li></a>        
  </ul>
</nav>