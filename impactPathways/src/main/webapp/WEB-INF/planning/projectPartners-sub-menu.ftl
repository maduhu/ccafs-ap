[#ftl] 
<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all"> 
    <li id="" class="partnerTab ui-state-default ui-corner-top [#if partnerStage == "partnerLead"] ui-tabs-active ui-state-active ui-state-hover[/#if]">
      <a href="[@s.url action='partnerLead' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> [@s.text name="planning.projectPartners.subMenu.partnerLead" /] </a>
    </li>
    <li id="" class="partnerTab ui-state-default ui-corner-top [#if partnerStage == "ppaPartners"] ui-tabs-active ui-state-active ui-state-hover[/#if]">
      <a href="[@s.url action='ppaPartners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> [@s.text name="planning.projectPartners.subMenu.ppaPartners" /] </a>
    </li> 
    <li id="" class="partnerTab ui-state-default ui-corner-top [#if partnerStage == "partners"] ui-tabs-active ui-state-active ui-state-hover[/#if]">
      <a href="[@s.url action='partners' includeParams='get'][@s.param name='projectID']${project.id?c}[/@s.param][/@s.url]"> [@s.text name="planning.projectPartners.subMenu.partners" /] </a>
    </li> 
</ul>