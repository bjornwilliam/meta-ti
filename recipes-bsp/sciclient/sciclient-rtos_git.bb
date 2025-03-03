SUMMARY = "TI RTOS low level driver for SCICLIENT"

inherit ti-pdk

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://sciclient.h;beginline=1;endline=31;md5=7224b2eeca1444815f1737bfcdfa892a"

COMPATIBLE_MACHINE = "k3"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SCICLIENT_GIT_URI = "git://git.ti.com/keystone-rtos/sciclient.git"
SCICLIENT_GIT_PROTOCOL = "git"
SCICLIENT_GIT_BRANCH = "master"

# Below commit ID corresponds to "DEV.SCICLIENT.01.00.00.04"
SCICLIENT_SRCREV = "287fe553ef548a60cce59e6a41d2e173ecbbc18a"

BRANCH = "${SCICLIENT_GIT_BRANCH}"
SRC_URI = "${SCICLIENT_GIT_URI};protocol=${SCICLIENT_GIT_PROTOCOL};branch=${BRANCH}"

SRCREV = "${SCICLIENT_SRCREV}"
PV = "01.00.00.04"
PR = "r0"

DEPENDS_append = " osal-rtos common-csl-ip-rtos"

# Build with make instead of XDC
TI_PDK_XDCMAKE = "0"

SCICLIENT_PACKAGE_BASE   = "${S}/../sciclient_base"
SCICLIENT_ROOTPATH = "${SCICLIENT_PACKAGE_BASE}/package/all/pdk_/packages/ti/drv/sciclient"

export PDK_INSTALL_PATH = "${PDK_INSTALL_DIR}/packages"
export PDK_SCICLIENT_ROOT_PATH = "${SCICLIENT_PACKAGE_BASE}/package/all/pdk_/packages"

# Sciclient for am65x supports mcu1_1 in addition to the default cores in TI_PDK_LIMIT_CORES
TI_PDK_LIMIT_CORES_append_am65xx = " mcu1_1"

# HTML doc link params
PDK_COMP_LINK_TEXT = "SCICLIENT"

do_configure() {

    rm -rf ${SCICLIENT_PACKAGE_BASE}
    cd ${S}

    # remove any previous package
    rm -f ${SCICLIENT_PACKAGE_BASE}

    # make the release package before building it
    make package BOARD="${TI_PDK_LIMIT_BOARDS}" DEST_ROOT=${SCICLIENT_PACKAGE_BASE} PDK_SCICLIENT_COMP_PATH=${S}

   # This is to ensure the make package completed successfully
    cat  ${SCICLIENT_ROOTPATH}/makefile
}

do_compile() {

    cd ${SCICLIENT_ROOTPATH}

    # Clean
    # make clean LIMIT_SOCS="${LIMSOCS}" LIMIT_BOARDS="${LIMBOARDS}"

    # Build am65xx libraries
    make lib xdc_meta doxygen LIMIT_SOCS="${TI_PDK_LIMIT_SOCS}" LIMIT_BOARDS="${TI_PDK_LIMIT_BOARDS}" LIMIT_CORES="${TI_PDK_LIMIT_CORES}"

    #archive
    tar -cf sciclient.tar --exclude='*.tar' ./*
}

do_install() {
    cd ${SCICLIENT_ROOTPATH}
    install -d ${D}${PDK_INSTALL_DIR_RECIPE}/packages/ti/drv/sciclient
    find -name "*.tar" -exec tar xf {} --no-same-owner -C ${D}${PDK_INSTALL_DIR_RECIPE}/packages/ti/drv/sciclient \;
}

FILES_${PN} += "${PDK_INSTALL_DIR_RECIPE}/packages"

INSANE_SKIP_${PN} = "arch ldflags file-rdeps"

INSANE_SKIP_${PN}-dbg = "arch"
