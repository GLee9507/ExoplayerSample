
cmake_minimum_required(VERSION 3.4.1)


set(SRC ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jni)
set(LIB_DIR ${SRC}/ffmpeg/android-libs/${ANDROID_ABI})

include_directories(${SRC}/ffmpeg)

add_library( avutil SHARED IMPORTED )
set_target_properties( avutil PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libavutil.so )

add_library( avcodec SHARED IMPORTED )
set_target_properties( avcodec PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libavcodec.so )

add_library( avresample SHARED IMPORTED )
set_target_properties( avresample PROPERTIES IMPORTED_LOCATION ${LIB_DIR}/libavresample.so )

add_library( # Sets the name of the library.
             ffmpeg

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${SRC}/ffmpeg_jni.cc )



find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


target_link_libraries( # Specifies the target library.
                        ffmpeg
                       avutil
                       avcodec
                       avresample

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )