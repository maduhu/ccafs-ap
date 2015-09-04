[#ftl]
[#macro projectPartner projectPartner={} projectPartnerName="" projectPartnerIndex="-1" template=false ]
  <div id="projectPartner-${template?string('template',(projectPartner.id)!)}" class="projectPartner borderBox ${(projectPartner.leader?string('leader',''))!} ${(projectPartner.coordinator?string('coordinator',''))!}" style="display:${template?string('none','block')}">
    <div class="loading" style="display:none"></div>
    [#-- Edit Button  --]
    [#if (!editable && canEdit)]
      <div class="editButton"><a href="[@s.url][@s.param name ="projectID"]${project.id}[/@s.param][@s.param name="edit"]true[/@s.param][/@s.url]#projectPartner-${(projectPartner.id)!}">[@s.text name="form.buttons.edit" /]</a></div>
    [/#if]
    
    [#-- Remove link for all partners --]
    [#if editable]
      <div class="removeLink"><div id="removePartner" class="removePartner removeElement removeLink" title="[@s.text name="preplanning.projectPartners.removePartner" /]"></div></div>
    [/#if]
    
    <div class="leftHead">
      <span class="index">${projectPartnerIndex?number+1}</span>
      <span class="elementId">Partner  <strong class="type"> ${(projectPartner.leader?string('(Leader)',''))!} ${(projectPartner.coordinator?string('(Coordinator)',''))!}</strong></span>
    </div>
    <input id="id" class="partnerId" type="hidden" name="${projectPartnerName}[${projectPartnerIndex}].id" value="${(projectPartner.id)!-1}" />

    [#-- Filters --]
    [#if editable && template]
      <div class="filters-link">[@s.text name="preplanning.projectPartners.filters" /]</div>
      <div class="filters-content">
        [#-- Partner type list --]
        <div class="halfPartBlock partnerTypeName chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.partnerType" listName="intitutionTypes" keyFieldName="id"  displayFieldName="name" className="partnerTypes" value="${(projectPartner.institution.type.id)!-1}" /]
        </div>
        [#-- Country list --]
        <div class="halfPartBlock countryListBlock chosen">
          [#-- Name attribute is not needed, we just need to load the value, not save it it. --]
          [@customForm.select name="" label="" disabled=!editable i18nkey="preplanning.projectPartners.country" listName="countries" keyFieldName="id"  displayFieldName="name" className="countryList" value="'${(projectPartner.institution.country.id)!-1}'" /]
        </div>
      </div> 
    [/#if]
    
    [#-- Organization  --]
    <div class="fullPartBlock partnerName chosen">
      <p class="fieldError"></p>
      [@customForm.select name="${projectPartnerName}[${projectPartnerIndex}].institution" value="${(projectPartner.institution.id)!-1}" className="institutionsList" required=true  disabled=!editable i18nkey="preplanning.projectPartners.partner.name" listName="allInstitutions" keyFieldName="id"  displayFieldName="getComposedName()" editable=(editable && template) /]
      [#if editable && !template]
        <input class="institutionsList" type="hidden" name="${projectPartnerName}[${projectPartnerIndex}].institution" value="${(projectPartner.institution.id)!-1}" />
      [/#if]
    </div>
    
    [#-- Indicate which PPA Partners for second level partners --]
    [#assign showPPABlock][#if (projectPartner.institution.PPA)!true]none[#else]block[/#if][/#assign]
    <div class="ppaPartnersList panel tertiary" style="display:${showPPABlock}">
      <div class="panel-head">[@customForm.text name="preplanning.projectPartners.indicatePpaPartners" readText=!editable /]</div> 
      <div class="panel-body">
        [#if !(projectPartner.partnerContributors?has_content) && !editable]
          <p>[@s.text name="planning.projectPartners.noSelectedCCAFSPartners" /] </p>
        [/#if]
        <ul class="list"> 
        [#if projectPartner.partnerContributors?has_content]
          [#list projectPartner.partnerContributors as ppaPartner]
            <li class="clearfix [#if !ppaPartner_has_next]last[/#if]">
              <input class="id" type="hidden" name="${projectPartnerName}[${projectPartnerIndex}].partnerContributors[${ppaPartner_index}].institution.id" value="${ppaPartner.id}" />
              <span class="name">${(ppaPartner.institution.composedName)!}</span> 
              [#if editable]<span class="listButton remove">[@s.text name="form.buttons.remove" /]</span>[/#if]
            </li>
          [/#list]
        [/#if]
        </ul>
        [#if editable]
          [@customForm.select name="" label="" disabled=!canEdit i18nkey="" listName="" keyFieldName="id"  displayFieldName="getComposedName()" className="ppaPartnersSelect" value="" /]
        [/#if] 
      </div>
    </div>
    
    [#-- Contacts person  --]
    <div class="contactsPerson panel tertiary">
      <div class="panel-head">[@s.text name="planning.projectPartners.projectPartnerContacts" /]</div>
      <div class="fullPartBlock">
      [#if projectPartner.partnerPersons?has_content]
        [#list projectPartner.partnerPersons as partnerPerson]
          [@contactPerson contact=partnerPerson contactName="${projectPartnerName}[${projectPartnerIndex}].partnerPersons" contactIndex="${partnerPerson_index}" /]
        [/#list]
      [#else]
        [@contactPerson contactName="${projectPartnerName}[${projectPartnerIndex}].partnerPersons" contactIndex="0" /]
      [/#if]  
      [#if (editable && canEdit)]
        <div class="addContact"><a href="" class="addLink">[@s.text name="planning.projectPartners.addContact"/]</a></div> 
      [/#if]
      </div>
    </div>
    
  </div>
[/#macro]

[#macro contactPerson contact={} contactName="" contactIndex="-1" template=false]
  <div id="contactPerson-${template?string('template',(contact.id)!)}" class="contactPerson simpleBox ${(contact.type)!}" style="display:${template?string('none','block')}">
    [#-- Remove link for all partners --]
    [#if editable]
      <div class="removePerson removeElement" title="[@s.text name="planning.projectPartners.removePerson" /]"></div>
    [/#if]
    <div class="leftHead">
      <span class="index"></span>
    </div>
    <input id="id" class="partnerPersonId" type="hidden" name="${contactName}[${contactIndex}].id" value="${(contact.id)!-1}" />
    
    [#-- Partner Person type and email--]
    <div class="fullPartBlock"> 
      <div class="partnerPerson-type halfPartBlock clearfix">
      [@customForm.select name="${contactName}[${contactIndex}].type" className="partnerPersonType" disabled=!canEdit i18nkey="planning.projectPartners.personType" listName="partnerPersonTypes" value="'${(contact.type)!-1}'" editable=editable /]
      [#if !editable]<div class="select"><p>[@s.text name="planning.projectPartners.types.${(contact.type)!'none'}"/]</p></div>[/#if]
      </div>
      <div class="partnerPerson-email userField halfPartBlock clearfix">
        [#-- Contact Person information is going to come from the users table, not from project_partner table (refer to the table project_partners in the database) --] 
        [@customForm.input name="" value="${(contact.user.composedName?html)!}" className="userName" type="text" disabled=!canEdit i18nkey="preplanning.projectPartners.contactPersonEmail" required=!project.bilateralProject readOnly=true editable=editable/]
        <input class="userId" type="hidden" name="${contactName}[${contactIndex}].user.id" value="${(contact.user.id)!'-1'}" />   
        [#if editable]<div class="searchUser">[@s.text name="form.buttons.searchUser" /]</div>[/#if]
      </div>
    </div>
    
    [#-- Responsibilities --] 
    <div class="fullPartBlock partnerResponsabilities chosen"> 
      [@customForm.textArea name="${contactName}[${contactIndex}].responsibilities" className="resp" i18nkey="preplanning.projectPartners.responsabilities" required=!project.bilateralProject editable=editable /]
    </div>
    
    [#if !template]
      [#-- Activities leading and Deliverables with responsibilities --]
      <div class="contactTags fullPartBlock clearfix">
        [#if (contact.user.id??)!false ]
          [#if action.getActivitiesLedByUser(contact.user.id)?has_content]
            <div class="tag activities">[@s.text name="planning.projectPartners.personActivities"][@s.param]${action.getActivitiesLedByUser(contact.user.id)?size}[/@s.param][/@s.text]</div>
            <ul class="activitiesList" style="display:none">
            [#list action.getActivitiesLedByUser(contact.user.id) as activity]
              <li><a href="[@s.url namespace=namespace action='activities' ][@s.param name='${projectRequest}']${project.id?c}[/@s.param][/@s.url]#activity-${activity.id}">${activity.title}</a></li>
            [/#list]
            </ul>
          [/#if]
          [#if action.getDeliverablesLedByUser(contact.user.id)?has_content]
            <div class="tag deliverables">[@s.text name="planning.projectPartners.personDeliverables"][@s.param]${action.getDeliverablesLedByUser(contact.user.id)?size}[/@s.param][/@s.text]</div>
            <ul class="deliverablesList" style="display:none">
            [#list action.getDeliverablesLedByUser(contact.user.id) as deliverable]
              <li><a href="[@s.url namespace=namespace action='deliverable' ][@s.param name='deliverableID']${deliverable.id}[/@s.param][/@s.url]">${deliverable.title}</a></li>
            [/#list]
            </ul>
          [/#if]
        [/#if]
      </div>
    [/#if]
  </div>
[/#macro]
