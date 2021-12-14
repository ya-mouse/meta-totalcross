DEPENDS = "totalcross-vm-core-native apache-maven-native openjdk-8-native"

TOTALCROSS_APP_DIR_NAME ?= "${datadir}/${PN}"
TOTALCROSS_APP_NAME ?= "${PN}"
TOTALCROSS_RUN_OPTIONS ?= ""

do_configure[noexec] = "1"
# do_compile[noexec] = "1"

totalcross_install() {
    install -d ${D}${TOTALCROSS_APP_DIR_NAME}
    for f in TCBase.tcz TCFont.tcz TCUI.tcz; do \
      ln -s ${libdir}/totalcross/${f} ${D}${TOTALCROSS_APP_DIR_NAME}/${f}; \
    done
    ln -s ${libdir}/totalcross/totalcross-launcher ${D}${TOTALCROSS_APP_DIR_NAME}/${TOTALCROSS_APP_NAME}
}
do_install[postfuncs] += "totalcross_install"

totalcross_run_script() {
    cat > ${WORKDIR}/totalcross-run <<EOF
#!/bin/sh

export ${TOTALCROSS_RUN_OPTIONS}
cd ${TOTALCROSS_APP_DIR_NAME}
exec ${TOTALCROSS_APP_DIR_NAME}/${TOTALCROSS_APP_NAME}
EOF

    install -Dm 0755 ${WORKDIR}/totalcross-run ${D}${bindir}/totalcross-${TOTALCROSS_APP_NAME}
}
do_install[postfuncs] += "totalcross_run_script"

FILES_${PN} += "${TOTALCROSS_APP_DIR_NAME}"

RDEPENDS_${PN} += "totalcross-vm totalcross-vm-core totalcross-launcher"

INSANE_SKIP_${PN} += "already-stripped ldflags"
