/*
 * Copyright 2010-2012 the original author or authors.
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

/**
 * @author Andres Almiray
 */
class JxlayerGriffonPlugin {
    // the plugin version
    String version = '1.0.0'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '1.0.0 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [swing: '1.0.0']
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, qt
    List toolkits = ['swing']
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-jxlayer-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Provides JXLayer/JLayer integration'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
Adds JXLayer/JLayer support nodes depending on the current JVM. JXLayer is available in JDK6 and lower.
JLayer is available in JDK7 and higher.

Usage
-----

| Node                  | Property         | Type            | Default             | Required | Bindable | Notes                                                                              |
| --------------------- | ---------------- | --------------- | ------------------- | -------- | -------- | ---------------------------------------------------------------------------------- |
| jxlayer               | view             | JComponent      |                     | no       | yes      | or nest a target component                                                         |
|                       | UI               | LayerUI         |                     | no       | no       | or nest one of the UI nodes                                                        |
|                       | glassPane        | JPanel          |                     | no       | yes      |                                                                                    |
| buttonPanelUI         | focusCyclic      | boolean         | false               | no       | no       | Not available in JDK7+                                                             |
| debugRepaintingUI     |                  |                 |                     |          |          | Not available in JDK7+                                                             |
| mouseScrollableUI     |                  |                 |                     |          |          | Not available in JDK7+                                                             |
| spotLightUI           | overlayColor     | Color           | Color(0, 0, 0, 128) | no       | yes      | Not available in JDK7+                                                             |
|                       | softClipWidth    | int             | 0                   | no       | yes      |                                                                                    |
|                       | enabled          | boolean         | true                | no       | yes      |                                                                                    |
|                       |                  |                 |                     |          |          | you can nest any number of Shapes (use `bean()` node)                              |
| lockableUI            | locked           | boolean         | false               | no       | yes      | Not available in JDK7+                                                             |
|                       |                  |                 |                     |          |          | you can nest any number of LayerEffect (use `bean()` node)                         |
| bufferedImageOpEffect | bufferedImageOps | BufferedImageOp |                     | no       | yes      | or nest any number of `BufferedImageOp` (use `bean()` node) Not available in JDK7+ |


### Example

__BuildConfig.groovy__

        griffon.project.dependency.resolution = {
            inherits "global"
            log "warn"
            repositories {
                griffonHome()
                mavenCentral()
            }
            dependencies {
                compile 'org.codehaus.griffon:jsilhouette-geom:0.4',
                        'com.jhlabs:filters:2.0.235'
            }
        }

__SampleView.groovy__

        import javax.swing.SwingConstants
        import org.codehaus.griffon.jsilhouette.geom.*
        import com.jhlabs.image.*

        def lipsum = """Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
        nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
        fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
        culpa qui officia deserunt mollit anim id est laborum.
        """

        createContent = {
           scrollPane(preferredSize: [300, 300] ) {
              textArea(lineWrap: true, wrapStyleWord: true, text: lipsum*3, columns: 40)
           }
        }

        application(title: 'JXLayer',
          size: [460, 400],
          locationByPlatform:true,
          iconImage: imageIcon('/griffon-icon-48x48.png').image,
          iconImages: [imageIcon('/griffon-icon-48x48.png').image,
                       imageIcon('/griffon-icon-32x32.png').image,
                       imageIcon('/griffon-icon-16x16.png').image]) {
           tabbedPane(tabPlacement: SwingConstants.LEFT) {
              panel(title: 'MouseScrollableUI') {
                 borderLayout()
                 jxlayer(constraints: CENTER) {
                    mouseScrollableUI()
                    createContent()
                 }
                 label('Press middle-mouse button and scroll.', constraints: SOUTH)
              }
              panel(title: 'SpotLightUI') {
                 borderLayout()
                 jxlayer(constraints: CENTER) {
                    spotLightUI(id: 'spotLight') {
                       bean(new Star(), cx: 150f, cy: 150f, or: 100f, ir: 50f, count: 5i)
                    }
                    createContent()
                 }
              }
              panel(title: 'LockableUI') {
                 borderLayout()
                 jxlayer(constraints: CENTER) {
                    lockableUI(id: 'lock') {
                       bufferedImageOpEffect {
                          bean(new BlurFilter())
                       }
                    }
                    createContent()
                 }
                 checkBox('Lock', constraints: SOUTH,
                    actionPerformed: {e-> lock.locked = e.source.selected})
              }
           }
        }

[1]: http://code.google.com/p/jbusycomponent
'''
}
