#!/bin/bash

brew install r

echo "install.packages(\"ggplot2\", repos=\"https://cran.rstudio.com\")" | R --no-save
echo "install.packages(\"plotly\", repos=\"https://cran.rstudio.com\")" | R --no-save
echo "install.packages(\"processx\", repos=\"https://cran.rstudio.com\")" | R --no-save

