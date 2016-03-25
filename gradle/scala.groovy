/*
 * Copyright (c) 2015. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
apply plugin: 'scala'

sourceCompatibility = '1.8'
targetCompatibility = '1.8'

compileScala.options*.compilerArgs = ['-Xlint:deprecation','-encoding', 'UTF-8']
compileTestScala.options*.compilerArgs = ['-Xlint:deprecation','-encoding', 'UTF-8']

ext.sharedManifest = manifest {
    attributes( 'Implementation-Title': project.name, 'Implementation-Version': project.version )
}

jar {
    manifest = project.manifest {
        from sharedManifest
    }

    from( buildDir ) {
        include 'build-info.properties'
    }
}

jar.dependsOn 'createBuildInformationFile'

task sourcesJar(type: Jar, dependsOn: classes) {
    classifier = 'sources'
    from sourceSets.main.allSource
    manifest = project.manifest {
        from sharedManifest
    }
}

task testSourcesJar(type: Jar, dependsOn: testClasses) {
    classifier = 'test-sources'
    from sourceSets.test.allSource
    manifest = project.manifest {
        from sharedManifest
    }
}

task testBinariesJar(type: Jar, dependsOn: testClasses) {
    classifier = 'test'
    from sourceSets.test.output
    manifest = project.manifest {
        from sharedManifest
    }
}

task scaladocJar(type: Jar, dependsOn: scaladoc) {
    classifier = 'scaladoc'
    from project.docsDir.path + '/scaladoc'
    manifest = project.manifest {
        from sharedManifest
    }
}

artifacts {
    archives sourcesJar
    archives testSourcesJar
    archives testBinariesJar
    archives scaladocJar
}

test {
    useJUnit {
        includeCategories 'org.kurron.categories.UnitTest'
    }
    testLogging {
        showStandardStreams = false
        exceptionFormat = 'full'
    }
    reports.html.destination =  "$buildDir/reports/unitTests"
}

