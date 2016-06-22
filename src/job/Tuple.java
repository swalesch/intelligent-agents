package job;

public class Tuple<FIRST, SECOND> {

	private FIRST _valueFirst;
	private SECOND _valueSecond;

	public Tuple(FIRST valueFirst, SECOND valueSecond) {
		_valueFirst = valueFirst;
		_valueSecond = valueSecond;
	}

	public FIRST getValueFirst() {
		return _valueFirst;
	}

	public SECOND getValueSecond() {
		return _valueSecond;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_valueFirst == null) ? 0 : _valueFirst.hashCode());
		result = prime * result + ((_valueSecond == null) ? 0 : _valueSecond.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (_valueFirst == null) {
			if (other._valueFirst != null)
				return false;
		} else if (!_valueFirst.equals(other._valueFirst))
			return false;
		if (_valueSecond == null) {
			if (other._valueSecond != null)
				return false;
		} else if (!_valueSecond.equals(other._valueSecond))
			return false;
		return true;
	}

}
