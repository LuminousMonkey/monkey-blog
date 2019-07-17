#!/bin/bash

s3cmd -FM sync blog/ s3://luminousmonkey.org --rexclude '.*\.(map|scss)'
