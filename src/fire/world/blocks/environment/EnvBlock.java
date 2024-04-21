package fire.world.blocks.environment;

public class EnvBlock extends mindustry.world.Block{

    public EnvBlock(String name){
        super(name);
        requirements(mindustry.type.Category.effect, mindustry.world.meta.BuildVisibility.hidden, mindustry.type.ItemStack.with());
        update = true;
        targetable = false;
        forceDark = true;
        hasShadow = false;
        size = 1;
    }

    @Override
    public boolean canBreak(mindustry.world.Tile tile){
        return false;
    }

    public static class EnvBlockBuild extends mindustry.gen.Building{

        @Override
        public void update(){
            super.update();

            if(isStarter())
                updateStart();
            else
                updateStop();
        }

        protected void updateStart(){}

        protected void updateStop(){}

        /** Use position to decide to start or to stop, to prevent registering two blocks. */
        protected boolean isStarter(){
            return tileX() % 2 == 0;
        }
    }
}
