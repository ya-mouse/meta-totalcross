TOTALCROSS_APP_NAME = "BadAppleTC"

inherit totalcross-app

DESCRIPTION = "TotalCross Bad Apple sample"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=44093a3b6064dede98fba8d283888c80"

SRCREV = "master"
SRC_URI = "git://github.com/TotalCross/embedded-samples.git;protocol=https"
PN = "totalcross-sample-bad-apple"

S = "${WORKDIR}/git/embedded-samples/bad-apple"
B = "${S}"

do_compile() {
    JAVA_HOME="${STAGING_DIR_NATIVE}/usr/lib/jvm/openjdk-8-native" mvn package
#    java -cp ${STAGING_DIR_NATIVE}/usr/lib/totalcross/totalcross-sdk.jar tc.Deploy ${S}/target/BadAppleTC.jar -linux_arm /p
}

do_install() {
    install -dm 0755 ${D}${TOTALCROSS_APP_DIR_NAME}
    for f in LitebaseLib.tcz Material\ Icons.tcz ${TOTALCROSS_APP_NAME}.tcz; do \
        install -m 0644 "${S}/target/install/linux_arm/${f}" "${D}${TOTALCROSS_APP_DIR_NAME}/${f}"; \
    done
    install -m 0755 ${S}/target/install/linux_arm/${TOTALCROSS_APP_NAME} ${D}${TOTALCROSS_APP_DIR_NAME}/${TOTALCROSS_APP_NAME}
}
