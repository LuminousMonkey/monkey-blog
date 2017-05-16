[:site-title "LuminousMonkey"
 :site-description "Just me, talking crap."
 :site-url "http://luminousmonkey.org/"
 :site-author "Mike Aldred"
 :in-dir "resources/"
 :out-dir "html/"
 :default-template "default.clj"
 :post-out-subdir ""
 :default-extension "html"
 :encoding "UTF-8"
 :posts-per-page 5
 :blog-as-index true
 :emacs "/usr/bin/emacs"
 :emacs-eval ['(package-initialize)
              '(add-to-list 'package-archives '(("elpa" . "http://tromey.com/elpa/")
                                                ("gnu" . "http://elpa.gnu.org/packages/")
                                                ("marmalade" . "http://marmalade-repo.org/packages/")))
              '(unless (package-installed-p 'use-package)
                       (package-refresh-contents)
                       (package-install 'use-package))
              '(require 'use-package)
              '(use-package clojure-mode)
              '(use-package clojure-mode-extra-font-locking)
              '(use-package org :ensure t)
              '(require 'ox-html)
              '(defun org-custom-link-img-follow (path)
                 (org-open-file-with-emacs
                  (format "../images/%s" path)))
              '(defun org-custom-link-img-export (path desc format)
                 (cond
                   ((eq format 'html)
                    (format "<img src=\"/images/%s\" alt=\"%s\">" path desc))))
              '(org-add-link-type "img" 'org-custom-link-img-follow 'org-custom-link-img-export)
              '(setq org-html-htmlize-font-prefix "")
              '(setq org-html-htmlize-output-type 'css)]]
