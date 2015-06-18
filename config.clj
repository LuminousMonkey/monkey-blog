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
 :org-export-command "(progn
                       (eval-and-compile
                         (mapc
                          #'(lambda (path)
                              (push (expand-file-name path user-emacs-directory) load-path))
                          '(\"site-lisp\" \"site-lisp/use-package\" \"override\" \"lisp\")))
                       (eval-and-compile
                         (require 'use-package)
                         (require 'ox-html))
                       (use-package clojure-mode :load-path \"site-lisp/clojure-mode\")
                       (setq org-html-htmlize-font-prefix \"\")
                       (setq org-html-htmlize-output-type 'css)
                       (org-html-export-as-html nil nil nil t nil)
                       (with-current-buffer \"*Org HTML Export*\"
                         (princ (org-no-properties (buffer-string)))))"]
