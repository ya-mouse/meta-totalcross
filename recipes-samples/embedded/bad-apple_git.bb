TOTALCROSS_APP_NAME = "BadApple"
TOTALCROSS_RUN_OPTIONS = "SDL_RENDER_DRIVER=opengles2"

inherit totalcross-app

DESCRIPTION = "TotalCross Bad Apple sample"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.md;md5=4654a7673596c3f8ff6dded4ba21c83a"

SRCREV = "6585960dbbebdbc8c6cc0111d791d061852e5b6f"
SRC_URI = "git://github.com/TotalCross/BadApple.git;protocol=https;branch=topic/improve-file-reading"
PN = "totalcross-sample-bad-apple"

S = "${WORKDIR}/git"
B = "${S}"

do_compile() {
    JAVA_HOME="${STAGING_DIR_NATIVE}/usr/lib/jvm/openjdk-8-native" mvn package
}

do_install() {
    install -dm 0755 ${D}${TOTALCROSS_APP_DIR_NAME}
    for f in LitebaseLib.tcz Material\ Icons.tcz ${TOTALCROSS_APP_NAME}.tcz; do \
        install -m 0644 "${S}/target/install/linux_arm/${f}" "${D}${TOTALCROSS_APP_DIR_NAME}/${f}"; \
    done
    install -m 0755 ${S}/target/install/linux_arm/${TOTALCROSS_APP_NAME} ${D}${TOTALCROSS_APP_DIR_NAME}/${TOTALCROSS_APP_NAME}
    for d in init.d rc3.d rc5.d; do \
        install -dm 0755 ${D}/etc/${d}; \
    done
    cat <<EOF>${D}/etc/init.d/bad-apple
#!/bin/sh
${bindir}/totalcross-${TOTALCROSS_APP_NAME} &
EOF
    chmod 0755 ${D}/etc/init.d/bad-apple
    ln -s ../init.d/bad-apple ${D}/etc/rc3.d/S02bad-apple
    ln -s ../init.d/bad-apple ${D}/etc/rc5.d/S02bad-apple
}
