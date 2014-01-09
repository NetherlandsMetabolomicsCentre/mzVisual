package nl.nmc.mzvisual

import grails.converters.JSON

class DemoController {

    def index() {

        def r = new Random()

        def data = []
        30.times { s ->

            while ("${s}".size() < 10) { s = "0${s}" }

            20.times { f ->

                while ("${f}".size() < 10) { f = "0${f}" }

                data << [
                    'sample': "sample_${s}",
                    'feature': "feature_${f}",
                    'intensity': "${r.nextInt(941)}.${r.nextInt(2343241)}" as Double
                ]
            }
        }

        render """
                <html>
                    <head>
                        <script src="http://d3js.org/d3.v3.min.js"></script>
                        <script src="http://dimplejs.org/dist/dimple.v1.1.3.min.js"></script>
                    </head>
                    <body>
                      <script type="text/javascript">
                        var svg = dimple.newSvg("body", document.documentElement.clientWidth, 650 );
                        var data = ${data as JSON};
                        var chart = new dimple.chart(svg, data);
                        chart.setBounds(60, 60, document.documentElement.clientWidth-100, 400)

                        var xAxis = chart.addCategoryAxis("x", "sample");
                        xAxis.addOrderRule("sample");

                        var yAxis = chart.addMeasureAxis("y", "intensity");
                        yAxis.tickFormat = ",g";

                        var scatter = chart.addSeries("feature", dimple.plot.scatter);
                        scatter.addOrderRule("feature");
                        chart.draw();

                        var myLegend = chart.addLegend(65, 1, document.documentElement.clientWidth-150, 300, "left", scatter);
                        chart.draw();
                        chart.legends = [];

                        // Get a unique list of Owner values to use when filtering
                        var filterValues = dimple.getUniqueValues(data, "feature");
                        // Get all the rectangles from our now orphaned legend
                        myLegend.shapes.selectAll("rect")
                          // Add a click event to each rectangle
                          .on("click", function (e) {
                            // This indicates whether the item is already visible or not
                            var hide = false;
                            var newFilters = [];
                            // If the filters contain the clicked shape hide it
                            filterValues.forEach(function (f) {
                              if (f === e.aggField.slice(-1)[0]) {
                                hide = true;
                              } else {
                                newFilters.push(f);
                              }
                            });
                            // Hide the shape or show it
                            if (hide) {
                              d3.select(this).style("opacity", 0.2);
                            } else {
                              newFilters.push(e.aggField.slice(-1)[0]);
                              d3.select(this).style("opacity", 0.8);
                            }
                            // Update the filters
                            filterValues = newFilters;
                            // Filter the data
                            chart.data = dimple.filterData(data, "feature", filterValues);
                            // Passing a duration parameter makes the chart animate. Without
                            // it there is no transition
                            chart.draw();
                          });
                      </script>
                    </body>
                </html>"""
    }
}
