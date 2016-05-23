#Introduction

Misspellings lead IR systems to provide bad results without users even realizing their mistakes. Users assume that the system lacks quality, which decreases their satisfaction and likelihood of returning to the system. We detected the misspelling problem in the Portuguese Web Archive. Hence, we analyzed existing solutions for spelling suggestion and integrated the solution that provided the best results in our user interface.

The following technical report Query Suggestion for Web Archive Search describes the adopted methodology, the obtained results and the chosen solution.

The datasets (in Portuguese) used can be downloaded from http://www.linguateca.pt/Repositorio/CorrOrtog/.

#Compile

Checkout PwaSpellchecker: * svn checkout http://pwa-technologies.googlecode.com/svn/trunk/pwa-technologies Install PwaSpellchecker: * cd pwa-technologies/PwaSpellchecker * mvn install

The WAR file is available in: * pwa-technologies/PwaSpellchecker/target/pwaspellchecker-1.0.0.war
Install

Step-by-step: * Install Hunspell from http://hunspell.sourceforge.net/ * Install the proper dictionaries available at http://wiki.services.openoffice.org/wiki/Dictionaries * (Portuguese dictionaries are available at http://natura.di.uminho.pt/download/sources/Dictionaries/) * Unzip all files to a directory. * Copy pwaspellchecker-1.0.0.war to servlet container (e.g. Tomcat) webapps directory * Configure webapps/pwaspellchecker/WEB-INF/web.xml * Test at http://machine:8080/pwaspellchecker/checker?query=xxx

    (where xxx is the query to test)

