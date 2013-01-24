<#import "/spring.ftl" as spring />
<#macro loadScripts>
	<!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-transition.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-alert.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-modal.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-dropdown.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-scrollspy.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-tab.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-tooltip.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-popover.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-button.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-collapse.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-carousel.js"/>"></script>
    <script src="<@spring.url relativeUrl="/resources/js/bootstrap-typeahead.js"/>"></script>
	<script src="<@spring.url relativeUrl="/resources/js/fitness-onload.js"/>"></script>
	<script src="<@spring.url relativeUrl="/resources/js/registration-validator.js"/>"></script>
	<script src="<@spring.url relativeUrl="/resources/js/slimScroll.js"/>"></script>
	<script src="<@spring.url relativeUrl="/resources/js/bootstrap-datepicker.js"/>"></script>
	
	<script src="<@spring.url relativeUrl="/resources/js/jquery.selectBoxIt.js"/>"></script>
	<script src="<@spring.url relativeUrl="/resources/js/bootstrap-fileupload.js"/>"></script>
</#macro>