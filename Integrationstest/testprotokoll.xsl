<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tp="http://www.archivator.de/testplan"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.archivator.de/testplan testplan.xsd">

	<xsl:output method="xml" encoding="UTF-8" indent="yes"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />

	<xsl:template match="tp:testplan">
		<html>
			<head>
				<title>Testprotokoll</title>
				<link type="text/css" rel="stylesheet" charset="UTF-8" href="testplan.css" />
				<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
			</head>
			<body>
				<h1>Testprotokoll für die Software {archivator}</h1>
				<table class="testat">
					<tr>
						<td>Datum</td><td/>
					</tr>
					<tr>
						<td>Prüfling (GIT-Version)</td><td/>
					</tr>
					<tr>
						<td>Prüfergebnis</td><td/>
					</tr>
					<tr>
						<td>Prüfer</td><td/>
					</tr>
					<tr>
						<td>Unterschrift</td><td/>
					</tr>
				</table>
				<ol class="testfälle">
				<xsl:for-each select="tp:testfall">
					<li>
						<h2>
							Testfall: <xsl:value-of select="@titel" />
						</h2>
						<h3>Vorraussetzungen</h3>
						<ol class="vorraussetzungen">
							<xsl:for-each select="tp:vorraussetzungen">
								<xsl:for-each select="tp:vorraussetzung">
									<li>
											<xsl:value-of select="." />
									</li>
								</xsl:for-each>
							</xsl:for-each>
						</ol>
						<h3>Durchführung</h3>
						<ol class="durchführungen">
							<xsl:for-each select="tp:durchführung/tp:schritt">
								<li class="schritte">
									<xsl:value-of select="./text()"></xsl:value-of>
									<xsl:if test="tp:ergebnisse/tp:ergebnis">
									<table class="ergebnisse">
										<tr><th>erwartetes Ergebnis</th><th>OK</th><th>Issues</th></tr>
										<xsl:for-each select="tp:ergebnisse/tp:ergebnis">
										<tr>
											<td>
												<xsl:value-of select="./text()"></xsl:value-of>
											</td>
											<td/>
											<td/>
										</tr>
										</xsl:for-each>
									</table>
									</xsl:if>
								</li>
							</xsl:for-each>
						</ol>
					</li>
				</xsl:for-each>
				</ol>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
