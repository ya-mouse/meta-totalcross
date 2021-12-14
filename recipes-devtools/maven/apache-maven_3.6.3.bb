DESCRIPTION = "Maven is a software project management and comprehension tool. Based on the concept " \
  "of a Project Object Model (POM), Maven can manage a project's build, reporting and documentation " \
  "from a central piece of information."
HOMEPAGE = "http://maven.apache.org"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=47b7ce43312b5e1e13a8edf5c31245ad"

SRC_URI = "http://ftp.byfly.by/pub/apache.org/maven/maven-3/${PV}/binaries/${BP}-bin.zip"

RDEPENDS_${PN} = "openjdk-8-native"

SRC_URI[md5sum] = "eb34034750df61282b4d7a51200ecbd4"
SRC_URI[sha256sum] = "444522b0af3a85e966f25c50adfcd00a1a6fc5fce79f503bff096e02b9977c2e"

do_configure() {
  sed -i '49ilocalRepository${STAGING_DATADIR_NATIVE}/maven-repository/localRepository' ${S}/conf/settings.xml
}

do_install() {
  install -d -m 0755 ${D}${bindir}/${PN}
  cp -ar * ${D}${bindir}/${PN}
  ln -sf ${PN}/bin/mvn ${D}${bindir}/mvn
}

BBCLASSEXTEND = "native"
