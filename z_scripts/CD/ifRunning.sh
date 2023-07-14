#!/bin/bash
# shellcheck disable=SC2009
if ps aux | grep -q "$1"; then
  echo "running"
  else
    echo "not running"
  fi