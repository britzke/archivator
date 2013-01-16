<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:tp="http://www.archivator.de/testplan"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.archivator.de/testplan testplan.xsd">

	<xsl:output method="xml" encoding="UTF-8" indent="yes" 
	doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>

	<xsl:template match="tp:testplan">
		<html>
			<head>
				<title>Testprotokoll</title>
				<link type="text/css" rel="stylesheet" charset="UTF-8" href="testplan.css"/>
			</head>
			<body>
				<table>
					<tr>
					</tr>
					<xsl:for-each select="tp:testfall">
						<tr>
							<th>Testfall: <xsl:value-of select="@titel"/>
						</th>
						</tr>
						<tr>
							<td>
								<table>
									<caption>Vorraussetzungen</caption>
									<xsl:for-each select="tp:vorraussetzungen">
										<xsl:for-each select="tp:vorraussetzung">
											<tr>
												<td>
													<xsl:value-of select="." />
												</td>
											</tr>
										</xsl:for-each>
									</xsl:for-each>
								</table>
								<table>
									<caption>Durchführung</caption>
									<tr><th>Schritte</th></tr>
									<xsl:for-each select="//tp:testplan/tp:testfall/tp:durchführung"></xsl:for-each>
									<xsl:for-each select="tp:durchführung/tp:schritt">
										<tr>
											<td>
												<xsl:value-of select="./tp:schritt"></xsl:value-of>
											</td>
											<td>
												<xsl:value-of select="."></xsl:value-of>
											</td>
										</tr>			
									</xsl:for-each>
								</table>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>