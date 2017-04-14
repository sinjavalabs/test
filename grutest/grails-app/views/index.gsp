<!doctype html>
<html>
<head>
<meta name="layout" content="main" />
<title>Welcome to Grails</title>

<asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
	<content tag="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown" role="button" aria-haspopup="true"
		aria-expanded="false">Application Status <span class="caret"></span></a>
		<ul class="dropdown-menu">
			<li><a href="#">Environment:
					${grails.util.Environment.current.name}</a></li>
			<li><a href="#">App profile:
					${grailsApplication.config.grails?.profile}</a></li>
			<li><a href="#">App version: <g:meta name="info.app.version" /></a>
			</li>
			<li role="separator" class="divider"></li>
			<li><a href="#">Grails version: <g:meta
						name="info.app.grailsVersion" /></a></li>
			<li><a href="#">Groovy version: ${GroovySystem.getVersion()}</a></li>
			<li><a href="#">JVM version:
					${System.getProperty('java.version')}</a></li>
			<li role="separator" class="divider"></li>
			<li><a href="#">Reloading active:
					${grails.util.Environment.reloadingAgentEnabled}</a></li>
		</ul></li>

	<li class="dropdown"><a href="#" class="dropdown-toggle"
		data-toggle="dropdown" role="button" aria-haspopup="true"
		aria-expanded="false">Controllers<span class="caret"></span></a>
		<ul class="dropdown-menu">
			<g:each var="c"
				in="${grailsApplication.controllerClasses.sort { it.fullName } }">
				<li class="controller"><g:link
						controller="${c.logicalPropertyName}">${c.name}</g:link></li>
			</g:each>
		</ul></li>
	</content>

	<div id="content" role="main">
		<section class="row colset-2-its">
			<h1>Bienvenido a BEA</h1>

			<p>Primer proyecto con Grails</p>

		</section>
	</div>

</body>
</html>
