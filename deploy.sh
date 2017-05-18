#!/bin/bash

s3cmd sync html/ s3://luminousmonkey.org --rexclude '.*\.(map|scss)'
