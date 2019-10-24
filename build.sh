#!/usr/bin/emacs --script

(load-file (concat (file-name-directory user-emacs-directory)
           "core/core-load-paths.el"))

(defvar bootstrap-version)
(let ((bootstrap-file
       (expand-file-name "straight/repos/straight.el/bootstrap.el" user-emacs-directory))
      (bootstrap-version 5))
  (unless (file-exists-p bootstrap-file)
    (with-current-buffer
        (url-retrieve-synchronously
         "https://raw.githubusercontent.com/raxod502/straight.el/develop/install.el"
         'silent 'inhibit-cookies)
      (goto-char (point-max))
      (eval-print-last-sexp)))
  (load bootstrap-file nil 'nomessage))

(straight-use-package 'use-package)

(setq straight-use-package-by-default t)

(setq use-package-always-defer t)

(setq-default tab-width 4)

;; Start Org Mode
(require 'org-mode-basic)
(require 'org-mode-organisation)
(require 'org-mode-templates)

(require 'programming-clojure)

(use-package ess
  :init (require 'ess-site)
  :config
  (org-babel-do-load-languages
   'org-babel-load-languages
   '((emacs-lisp . nil)
     (R . t))))

(use-package org-static-blog
  :load-path "override"
  :init
  (setq org-static-blog-publish-title "LuminousMonkey")
  (setq org-static-blog-publish-url "https://luminousmonkey.org/")
  (setq org-static-blog-publish-directory (concat (file-name-directory default-directory)
                                                  "blog/"))
  (setq org-static-blog-posts-directory (concat (file-name-directory default-directory)
                                                "posts/"))
  (setq org-static-blog-drafts-directory (concat (file-name-directory default-directory)
                                                 "drafts/"))
  (setq org-static-blog-enable-tags t)
  (setq org-export-with-toc nil)
  (setq org-export-with-section-numbers nil)
  (setq org-static-blog-use-semantic-html t))

(use-package htmlize
  :commands
  (htmlize-buffer
   htmlize-file
   htmlize-many-files
   htmlize-many-files-dired
   htmlize-region)
  :init
  (setq org-html-htmlize-output-type 'css))

(setq org-confirm-babel-evaluate nil)

(org-static-blog-publish)
