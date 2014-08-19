[#ftl]
[#macro activityDeliverable ]
	<div class="borderBox">
    	<div id="removeDeliverable-"  class="removeElement removeLink " title="[@s.text name="planning.deliverables.removeDeliverable" /]"></div>
    	<b>[@s.text name="planning.deliverables.expectedDeliverable" /]</b><br/><br/>
    	[#-- Title --] 
    	[@customForm.input name="" type="text" i18nkey="planning.deliverables.title" required=true /]
    	<div class="halfPartBlock">
    		[#-- Type --]
    		[@customForm.select name="deliverable" label=""  disabled=false i18nkey="planning.deliverables.type" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
    	<div class="halfPartBlock">
    		[#-- Year  --]
    		[@customForm.select name="deliverable" label=""  disabled=false i18nkey="planning.deliverables.year" listName="" keyFieldName="id"  displayFieldName="name" /]
    	</div>
		<div class="borderBox">
			<div id="removeActivityImpactPathway"class="removeElement removeLink" title="[@s.text name="planning.deliverables.removeNewUser" /]"></div>
			[#-- Next User --]
			[@customForm.input name="" type="text" i18nkey="planning.deliverables.nextUser" required=true /]<br/>
			[#-- Expected Changes --]
			[@customForm.textArea name="deliverable.expectedChanges" i18nkey="planning.deliverables.expectedChanges" required=true /]<br/>
			[#-- Strategies --]
			[@customForm.textArea name="deliverable.strategies" i18nkey="planning.deliverables.strategies" required=true /]<br/>
		</div>
		<div id="addActivityNextUser" class="addLink">
			<a href=""  class="addButton">[@s.text name="planning.deliverables.addNewUser" /]</a>
		</div>
	</div>
[/#macro]