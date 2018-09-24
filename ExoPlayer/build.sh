EXOPLAYER_ROOT="$(pwd)"
FFMPEG_EXT_PATH="${EXOPLAYER_ROOT}/extensions/ffmpeg/src/main"
NDK_PATH="/home/glee/Android/android-ndk-r15c"
#NDK_PATH="/mnt/d/ubuntu/android-ndk"
#FFMPEG_EXT_PATH="/mnt/d/android-project/ExoplayerSample/ExoPlayer/extensions/ffmpeg/src/main"
HOST_PLATFORM="linux-x86_64"
COMMON_OPTIONS="\
    --target-os=android \
    --disable-static \
    --enable-shared \
    --disable-doc \
    --disable-programs \
    --disable-everything \
    --disable-avdevice \
    --enable-avformat \
    --disable-swscale \
    --disable-postproc \
    --disable-avfilter \
    --disable-symver \
    --disable-swresample \
    --enable-avresample \
    --enable-decoder=vorbis \
    --enable-demuxer=vorbis \
    --enable-decoder=opus \
    --enable-demuxer=opus \
    --enable-decoder=ape\
    --enable-demuxer=ape\
    --enable-parser=ape\
    --enable-muxer=ape\
    " && \

cd "${FFMPEG_EXT_PATH}/jni" && \

#(proxychains4 git -C ffmpeg pull || proxychains4 git clone git://source.ffmpeg.org/ffmpeg ffmpeg) && \
cd ffmpeg && \
make clean && ./configure \
    --libdir=android-libs/armeabi-v7a \
    --arch=arm \
    --cpu=armv7-a \
    --incdir=/mnt/d/android-project/ExoplayerSample/ExoPlayer/extensions/ffmpeg/src/main/jni/includes \
    --cross-prefix="${NDK_PATH}/toolchains/arm-linux-androideabi-4.9/prebuilt/${HOST_PLATFORM}/bin/arm-linux-androideabi-" \
    --sysroot="${NDK_PATH}/platforms/android-19/arch-arm/" \
    --extra-cflags="-march=armv7-a -mfloat-abi=softfp" \
    --extra-ldflags="-Wl,--fix-cortex-a8" \
    --extra-ldexeflags=-pie \
    ${COMMON_OPTIONS} \
    && \
make -j4 && make install-libs

# make clean && ./configure \
#     --libdir=android-libs/arm64-v8a \
#     --arch=aarch64 \
#     --cpu=armv8-a \
#     --cross-prefix="${NDK_PATH}/toolchains/aarch64-linux-android-4.9/prebuilt/${HOST_PLATFORM}/bin/aarch64-linux-android-" \
#     --sysroot="${NDK_PATH}/platforms/android-21/arch-arm64/" \
#     --extra-ldexeflags=-pie \
#     ${COMMON_OPTIONS} \
#     && \
# make -j4 && make install-libs && \
# make clean && ./configure \
#     --libdir=android-libs/x86 \
#     --arch=x86 \
#     --cpu=i686 \
#     --cross-prefix="${NDK_PATH}/toolchains/x86-4.9/prebuilt/${HOST_PLATFORM}/bin/i686-linux-android-" \
#     --sysroot="${NDK_PATH}/platforms/android-9/arch-x86/" \
#     --extra-ldexeflags=-pie \
#     --disable-asm \
#     ${COMMON_OPTIONS} \
#     && \
# make -j4 && make install-libs && \