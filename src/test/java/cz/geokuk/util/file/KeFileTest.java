package cz.geokuk.util.file;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import cz.geokuk.util.file.KeFile.XRelativizeDubleDot;

public class KeFileTest {

	private static long NOW = System.currentTimeMillis();
	private static Root.Def PATTERN = new Root.Def(0, null, null);
	private static Root.Def PATTERN2 = new Root.Def(1, null, null);

	@Test(expected = NullPointerException.class)
	public void tesNull1() {
		new KeFile(null, new Root(new File("roo"), PATTERN));
	}

	@Test(expected = NullPointerException.class)
	public void tesNull11() {
		new KeFile(new FileAndTime(null, NOW), new Root(new File("roo"), PATTERN));
	}

	@Test(expected = NullPointerException.class)
	public void tesNull111() {
		new KeFile(new FileAndTime(new File((String) null), NOW), new Root(new File("roo"), PATTERN));
	}

	@Test(expected = NullPointerException.class)
	public void tesNull2() {
		new KeFile(new FileAndTime(new File("aaa"), NOW), null);
	}

	@Test(expected = NullPointerException.class)
	public void tesNull21() {
		new KeFile(new FileAndTime(new File("aaa"), NOW), new Root(null, PATTERN));
	}

	@Test(expected = NullPointerException.class)
	public void tesNull211() {
		new KeFile(new FileAndTime(new File("aaa"), NOW), new Root(new File((String) null), PATTERN));
	}

	@Test(expected = NullPointerException.class)
	public void tesNull22() {
		new KeFile(new FileAndTime(new File("aaa"), NOW), new Root(new File("aaa"), null));
	}

	@Test(expected = NullPointerException.class)
	public void tesNullRoot1() {
		new Root(null, PATTERN);
	}

	@Test(expected = NullPointerException.class)
	public void tesNullRoot11() {
		new Root(new File((String) null), PATTERN);
	}

	@Test(expected = NullPointerException.class)
	public void tesNullRoot2() {
		new Root(new File("aaa"), null);
	}

	// problém s testy na Unixech, nedokážeme zařídit, aby cesty nebyly navzájem ralitivizovtelné
	// @Test(expected=IllegalArgumentException.class)
	// public void testGetParent5() {
	// create("/c//jedna//bb", "d://dva//bb");
	// }

	@Test
	public void testEquals1() {
		final KeFile k1 = create("/c//aa//bb//ccc/ddd", "/c//aa//bb");
		final KeFile k2 = create("/c/aa//bb//ccc/ddd", "/c/aa//bb");
		Assert.assertTrue(k1.equals(k2));
		Assert.assertTrue(k2.equals(k1));
		Assert.assertEquals(k1.hashCode(), k2.hashCode());
	}

	@Test
	public void testEquals3() {
		final KeFile k1 = create("/c//aa//bb//ccc/ddd", "/c//aa//bb", NOW, PATTERN);
		final KeFile k2 = create("/c/aa//bb//ccc/ddd", "/c/aa//bb", 45456564, PATTERN);
		Assert.assertTrue(k1.equals(k2));
		Assert.assertTrue(k2.equals(k1));
		Assert.assertEquals(k1.hashCode(), k2.hashCode());
	}

	@Test
	public void testEquals4() {
		final KeFile k1 = create("/c//aa//bb//ccc/ddd", "/c//aa//bb", NOW, PATTERN);
		final KeFile k2 = create("/c/aa//bb//ccc/ddd", "/c/aa//bb", NOW, PATTERN2);
		Assert.assertTrue(k1.equals(k2));
		Assert.assertTrue(k2.equals(k1));
		Assert.assertEquals(k1.hashCode(), k2.hashCode());
	}

	@Test
	public void testGetLastModified() {
		final KeFile k = create("/c//aa//bb//ccc/ddd", "/c//aa//bb");
		Assert.assertEquals(NOW, k.getLastModified());
	}

	@Test
	public void testGetParent1() {
		final KeFile k = create("/c//aa//bb//ccc/ddd", "/c//aa//bb");
		assertFile("/c/aa/bb/ccc", k.getParent().getFile());
	}

	@Test
	public void testGetParent2() {
		final KeFile k = create("/c//aa//bb", "/c//aa//bb");
		Assert.assertNull(k.getParent());
	}

	@Test(expected = XRelativizeDubleDot.class)
	public void testGetParent3() {
		create("/c//jedna//bb", "/c//dva//bb");
	}

	@Test(expected = XRelativizeDubleDot.class)
	public void testGetParent4() {
		create("/c//aa//bb", "/c//aa//bb/cc/dd");
	}

	@Test
	public void testGetRelativePath() {
		final KeFile k = create("/c//aa//bb//ccc/ddd", "/c//aa//bb");
		Assert.assertEquals(Paths.get("ccc//ddd"), k.getRelativePath());
	}

	@Test
	public void testKeFile1() {
		final KeFile k = create("/c//aa//bb//ccc", "/c//aa//bb");
		assertFile("/c/aa/bb/ccc", k.getFile());
	}

	@Test
	public void testKeFile2() {
		final KeFile k = create("/c/aa/bb/ccc", "/c/aa/bb");
		assertFile("/c/aa/bb/ccc", k.getFile());
	}

	@Test
	public void testKeFile3() {
		final KeFile k = create("/c/aa/bb/ccc", "/c/aa/bb");
		assertFile("/c/aa/bb", k.root.dir);
	}

	@Test
	public void testNotEquals1() {
		final KeFile k1 = create("/c//aa//bb//ccXc/ddd", "/c//aa//bb");
		final KeFile k2 = create("/c/aa//bb//ccc/ddd", "/c/aa//bb");
		Assert.assertFalse(k1.equals(k2));
		Assert.assertFalse(k2.equals(k1));
	}

	@Test
	public void testNotEquals2() {
		final KeFile k1 = create("/c//aa//bb//ccc/ddd", "/c//aa");
		final KeFile k2 = create("/c/aa//bb//ccc/ddd", "/c/aa//bb");
		Assert.assertFalse(k1.equals(k2));
		Assert.assertFalse(k2.equals(k1));
	}

	@Test
	public void testNotEquals3() {
		final KeFile k1 = create("/c//aa//bb//ccXc/ddd1", "/c//aa//bb");
		final KeFile k2 = create("/c//aa//bb//ccXc/ddd2", "/c//aa//bb");
		Assert.assertFalse(k1.equals(k2));
		Assert.assertFalse(k2.equals(k1));
	}

	private void assertFile(final String fileName, final File file) {
		Assert.assertEquals(new File(fileName), file);
	}

	private KeFile create(final String file, final String root) {
		return create(file, root, NOW, PATTERN);
	}

	private KeFile create(final String file, final String root, final long cas, final Root.Def pattern) {
		return new KeFile(new FileAndTime(new File(file), cas), new Root(new File(root), pattern));
	}

}
