(ns first_noise.core
  (:use
    [overtone.live]))


;try some sins
(definst foo [] (saw 440))
(foo)

(definst baz [freq 440] (* 0.1 (saw freq))) ;(* 0.1 here means 10% power
(baz)

(stop)

(demo (sin-osc 440))
(odoc *)
(odoc +)
(odoc sin-osc)
(odoc kick)
(odoc odoc)

(definst sound_1 [freq 220] (saw freq))
(sound_1 220)
(stop)

;kicks
;\ep need at first line
(odoc *)
(odoc perc) ;beating sound
(odoc compander) ;makes high range sounds be able to play on speaker
(odoc let)

(definst kick [freq 120 dur 0.3 width 0.5]
    (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
                  env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
                  sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
                  src (sin-osc freq-env)
                  drum (+ sqr (* env src))]
          (compander drum drum 0.2 1 0.1 0.01 0.01)))
(kick)


;metro
(odoc metronome)
(odoc env-gen)

(defsynth kick [amp 0.5 decay 0.6 freq 65] 
    (let [env (env-gen (perc 0 decay) 1 1 0 1 FREE)
                  snd (sin-osc freq (* Math/PI 0.5) amp)]
          (out 0 (pan2 (* snd env) 0))))


(definst kick [amp 0.5 decay 0.6 freq 65] 
    (* (sin-osc freq (* Math/PI 1.5) amp)
            (env-gen (perc 0 decay) 1 1 0 1 FREE)))

(kick)

(:sdef kick) 




;sc-one
(demo 10 (sin-osc (+ 1000 (* 600 (lf-noise0:kr 12))) 0.3))
(odoc lf-noise0)

(demo 10 (rlpf (dust [12 15]) (+ 1600 (* 1500 (lf-noise1 [1/3, 1/4]))) 0.02 ))

(demo 10 (let [sines 5
              speed 6]
           (* (mix
               (map #(pan2 (* (sin-osc (* % 100))
                              (max 0 (+ (lf-noise1:kr speed) (line:kr 1 -1 30))))
                           (- (clojure.core/rand 2) 1))
                    (range sines)))
              (/ 1 sines))))

(demo 10 (let [noise (lf-noise1 3)
               saws  (mul-add (lf-saw [5 5.123]) 3 80)
               freq  (midicps (mul-add noise 24 saws))
               src   (* 0.4 (sin-osc freq))]
           (comb-n src 1 0.3 2)))

(demo (* 0.5 (sin-osc 100 (* 10 (sin-osc 500 0)))))

(demo (* 0.5 (pm-osc 100 500 10 0)))

(demo 10 (pm-osc 440 (mouse-y:kr 1 550) (mouse-x:kr 1 15)))

(demo 10
      (let [trigger       (line:kr :start 1, :end 20, :dur 60)
            freq          (t-rand:kr :lo 100, :hi 1000, :trig (impulse:kr trigger))
            num-harmonics (t-rand:kr :lo 1,   :hi 10,   :trig (impulse:kr trigger))
            amp           (linen:kr :gate (impulse:kr trigger) :attackTime 0, :susLevel 0.5, :releaseTime (/ 1 trigger))]
        (* amp (blip freq num-harmonics))))

