require recipes-browser/chromium/gn-utils.inc

DESCRIPTION = "A complete 2D graphic library for drawing Text, Geometries, and Images"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=822f02cc7736281816581cd064afbb1c"

DEPENDS = "\
    gn-native \
    ninja-native \
    \
    fontconfig \
    freetype \
    harfbuzz \
    icu \
    jpeg \
    libwebp \
    virtual/libgl \
"

inherit features_check pythonnative

REQUIRED_DISTRO_FEATURES = "opengl"

# chrome/m80
SRCREV = "b1fd250866425a6e1892473aeb0973caff6fcd05"
SRC_URI = "\
    git://skia.googlesource.com/skia.git;protocol=https;branch=chrome/m${PV} \
    file://m80-gn-remove-obsolete-function.patch \
"

S = "${WORKDIR}/git"

OUTPUT_DIR = "out/Release"
B = "${S}/${OUTPUT_DIR}"

GN_ARGS = '\
    ar="${AR}" \
    cc="${CC}" \
    cxx="${CXX}" \
    target_cpu="${@gn_target_arch_name(d)}" \
    is_official_build=true \
    skia_enable_pdf=false \
    skia_use_dng_sdk=false \
    is_component_build=true \
    skia_use_egl=true \
    extra_cflags=["-I${STAGING_INCDIR}/freetype2", "-I${STAGING_INCDIR}/harfbuzz"] \
'

do_configure() {
    gn gen --args='${GN_ARGS}' "${B}"
}

do_compile() {
    ninja -C "${B}"
}

do_install() {
    install -Dm 0644 ${B}/libskia.so ${D}${libdir}/libskia.so.${PV}
    ln -sf libskia.so.${PV} ${D}${libdir}/libskia.so

    install -Dm 0644 ${B}/libpathkit.a ${D}${libdir}/libpathkit.a

    install -d 0644 ${D}${includedir}/${PN}
    cp -rf ${S}/include ${D}${includedir}/${PN}/
    cp -rf ${S}/third_party ${D}${includedir}/${PN}/
}

# FIXME: This is a workaround as we're using an old release of Skia and doesn't
# make sense to spend too much time on this.
INSANE_SKIP_${PN} = "ldflags"
