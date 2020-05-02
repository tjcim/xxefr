<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
		
		<div class="container pt-5 pb-5" id="content">
			<h1>Malformed XML</h1>
			<p>What you submitted appears to include an XXE attack. The page where you submitted the attack is protected from XXE</p>
			<pre>
				<code class="java">
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
				</code>
			</pre>
		</div>