
Provides JXLayer/JLayer integration
-----------------------------------

Plugin page: [http://artifacts.griffon-framework.org/plugin/jxlayer](http://artifacts.griffon-framework.org/plugin/jxlayer)


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

