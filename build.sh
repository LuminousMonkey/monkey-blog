#!/usr/bin/emacs --script

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
(use-package org
  :init
  ;; Show the formatted text of *bold*, /italics/, but not the
  ;; characters used to mark then out.
  (setq org-hide-emphasis-markers t)

  ;; Show any source blocks in their native mode.
  (setq org-src-fontify-natively t)

  ;; Use CSS for any HTML output.
  (setq org-export-htmlize-output-type 'css))

(use-package clojure-mode
  :mode (("\\.edn$" . clojure-mode))
  :init
  (progn
    (use-package clojure-mode-extra-font-locking)

    (setq clojure--prettify-symbols-alist
          '(("fn" . ?λ)
            ("not=" . ?≠)
            ("identical?" . ?≡)
            ("<=" . ?≤)
            (">=" . ?≥)
            ("->" . (?- (Br . Bc) ?- (Br . Bc) ?>))
            ("->>" .  (?\s (Br . Bl) ?\s (Br . Bl) ?\s
                           (Bl . Bl) ?- (Bc . Br) ?- (Bc . Bc) ?>
                           (Bc . Bl) ?- (Br . Br) ?>))))

    (add-hook 'clojure-mode-hook 'prettify-symbols-mode)))

(use-package ess
  :init (require 'ess-site)
  :config
  (setq ess-ask-for-ess-directory nil)
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
