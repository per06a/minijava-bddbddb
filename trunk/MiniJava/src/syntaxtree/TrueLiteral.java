//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production: f0 -> "true"
 */
public class TrueLiteral implements Node {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2073082668832653979L;

	public NodeToken f0;

	public TrueLiteral(NodeToken n0) {
		f0 = n0;
	}

	public TrueLiteral() {
		f0 = new NodeToken("true");
	}

	public void accept(visitor.Visitor v) {
		v.visit(this);
	}

	public <R, A> R accept(visitor.GJVisitor<R, A> v, A argu) {
		return v.visit(this, argu);
	}

	public <R> R accept(visitor.GJNoArguVisitor<R> v) {
		return v.visit(this);
	}

	public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
		v.visit(this, argu);
	}
}
