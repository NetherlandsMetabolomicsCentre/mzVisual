package nl.nmc.mzvisual

import grails.converters.JSON

class DemoController {

    def index() {

          def data = [
            ['Sample':'A', 'Feature': 'NMC00001', 'Intensity': 23945],
            ['Sample':'B', 'Feature': 'NMC00002', 'Intensity': 21415],
            ['Sample':'C', 'Feature': 'NMC00003', 'Intensity': 14523],
            ['Sample':'A', 'Feature': 'NMC00001', 'Intensity': 34523],
            ['Sample':'C', 'Feature': 'NMC00002', 'Intensity': 23456],
            ['Sample':'F', 'Feature': 'NMC00003', 'Intensity': 67134]
          ] as JSON

          render """
                <html>
                    <head>
                        <script src=\"http://d3js.org/d3.v3.min.js\"></script>
                        <script src=\"http://dimplejs.org/dist/dimple.v1.1.1.min.js\"></script>
                    </head>
                    <body>
                      <script type=\"text/javascript\">
                        var svg = dimple.newSvg(\"body\", 1200, 600);
                        var data = ${data};
                        var chart = new dimple.chart(svg, data);
                        chart.addCategoryAxis(\"x\", \"Sample\");
                        chart.addMeasureAxis(\"y\", \"Intensity\");
                        chart.addSeries(\"Feature\", dimple.plot.scatter);
                        chart.draw();
                      </script>
                    </body>
                </html>"""
    }
}
