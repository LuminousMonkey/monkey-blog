#!/bin/bash

s3cmd sync blog/ s3://luminousmonkey.org --rexclude '.*\.(map|scss)'
