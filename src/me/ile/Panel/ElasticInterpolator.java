package me.ile.Panel;

/*
 *
 * Open source under the BSD License. 
 * 
 * Copyright © 2001 Robert Penner
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * Neither the name of the author nor the names of contributors may be used to endorse 
 * or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE. 
 *
 */


import me.ile.Panel.EasingType.Type;
import android.view.animation.Interpolator;

public class ElasticInterpolator implements Interpolator {

	private Type type;
	private float amplitude;
	private float period;

	public ElasticInterpolator(Type type, float amplitude, float period) {
		this.type = type;
		this.amplitude = amplitude;
		this.period = period;
	}

	public float getInterpolation(float t) {
		if (type == Type.IN) {
			return in(t, amplitude, period);
		} else if (type == Type.OUT) {
			return out(t, amplitude, period);
		} else if (type == Type.INOUT) {
			return inout(t, amplitude, period);
		}
		return 0;
	}

	private float in(float t, float a, float p) {
		if (t == 0) {
			return 0;
		}
		if (t >= 1) {
			return 1;
		}
		if (p == 0) {
			p = 0.3f;
		}
		float s;
		if (a == 0 || a < 1) {
			a = 1;
			s = p / 4;
		} else {
			s = (float) (p / (2 * Math.PI) * Math.asin(1 / a));
		}
		return (float) (-(a * Math.pow(2, 10 * (t -= 1)) * Math.sin((t - s)
				* (2 * Math.PI) / p)));
	}

	private float out(float t, float a, float p) {
		if (t == 0) {
			return 0;
		}
		if (t >= 1) {
			return 1;
		}
		if (p == 0) {
			p = 0.3f;
		}
		float s;
		if (a == 0 || a < 1) {
			a = 1;
			s = p / 4;
		} else {
			s = (float) (p / (2 * Math.PI) * Math.asin(1 / a));
		}
		return (float) (a * Math.pow(2, -10 * t)
				* Math.sin((t - s) * (2 * Math.PI) / p) + 1);
	}

	private float inout(float t, float a, float p) {
		if (t == 0) {
			return 0;
		}
		if (t >= 1) {
			return 1;
		}
		if (p == 0) {
			p = .3f * 1.5f;
		}
		float s;
		if (a == 0 || a < 1) {
			a = 1;
			s = p / 4;
		} else {
			s = (float) (p / (2 * Math.PI) * Math.asin(1 / a));
		}
		t *= 2;
		if (t < 1) {
			return (float) (-.5 * (a * Math.pow(2, 10 * (t -= 1)) * Math
					.sin((t - s) * (2 * Math.PI) / p)));
		} else {
			return (float) (a * Math.pow(2, -10 * (t -= 1))
					* Math.sin((t - s) * (2 * Math.PI) / p) * .5 + 1);
		}
	}
}
