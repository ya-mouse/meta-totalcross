FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_rpi = " file://xinitrc"

do_install_append_rpi() {
  install -m 0644 ${WORKDIR}/xinitrc ${D}/${sysconfdir}/X11/xinit/xinitrc
}
