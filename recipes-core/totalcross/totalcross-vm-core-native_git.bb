require totalcross-vm-core.inc
DESCRIPTION = "TotalCross SDK"

LIC_FILES_CHKSUM = "file://../LICENSE;md5=23c2a5e0106b99d75238986559bb5fc6"

DEPENDS = "openjdk-8-native"

S = "${WORKDIR}/git/TotalCrossSDK"
B = "${S}"

inherit native

export JAVA_HOME="${STAGING_DIR_NATIVE}/usr/lib/jvm/openjdk-8-native"

do_compile() {
    ${S}/gradlew dist
}

do_install() {
    install -Dm 0644 ${B}/dist/totalcross-sdk.jar ${D}${libdir}/totalcross/totalcross-sdk.jar
}

FILES_${PN} += "${libdir}/totalcross/"
