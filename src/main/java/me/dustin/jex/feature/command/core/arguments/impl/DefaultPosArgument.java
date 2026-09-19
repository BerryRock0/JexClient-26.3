package me.dustin.jex.feature.command.core.arguments.impl;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.dustin.jex.feature.command.core.arguments.Vec3ArgumentType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class DefaultPosArgument implements PosArgument {
   private final Coordinates x;
   private final Coordinates y;
   private final Coordinates z;

   public DefaultPosArgument(Coordinates x, Coordinates y, Coordinates z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3d toAbsolutePos(FabricClientCommandSource source) {
      Vec3 vec3d = source.getPlayer().getPos();
      return new Vec3(this.x.toAbsoluteCoordinate(vec3d.x), this.y.toAbsoluteCoordinate(vec3d.y), this.z.toAbsoluteCoordinate(vec3d.z));
   }

   public Vec2f toAbsoluteRotation(FabricClientCommandSource source) {
      Vec2 vec2f = source.getPlayer().getRotationClient();
      return new Vec2((float)this.x.toAbsoluteCoordinate((double)vec2f.x), (float)this.y.toAbsoluteCoordinate((double)vec2f.y));
   }

   public boolean isXRelative() {
      return this.x.isRelative();
   }

   public boolean isYRelative() {
      return this.y.isRelative();
   }

   public boolean isZRelative() {
      return this.z.isRelative();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DefaultPosArgument)) {
         return false;
      } else {
         DefaultPosArgument defaultPosArgument = (DefaultPosArgument)o;
         if (!this.x.equals(defaultPosArgument.x)) {
            return false;
         } else {
            return !this.y.equals(defaultPosArgument.y) ? false : this.z.equals(defaultPosArgument.z);
         }
      }
   }

   public static DefaultPosArgument parse(StringReader reader) throws CommandSyntaxException {
      int i = reader.getCursor();
      CoordinateArgument coordinateArgument = CoordinateArgument.parse(reader);
      if (reader.canRead() && reader.peek() == ' ') {
         reader.skip();
         CoordinateArgument coordinateArgument2 = CoordinateArgument.parse(reader);
         if (reader.canRead() && reader.peek() == ' ') {
            reader.skip();
            CoordinateArgument coordinateArgument3 = CoordinateArgument.parse(reader);
            return new DefaultPosArgument(coordinateArgument, coordinateArgument2, coordinateArgument3);
         } else {
            reader.setCursor(i);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
         }
      } else {
         reader.setCursor(i);
         throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
      }
   }

   public static DefaultPosArgument parse(StringReader reader, boolean centerIntegers) throws CommandSyntaxException {
      int i = reader.getCursor();
      Coordinates coordinateArgument = Coordinates.parse(reader, centerIntegers);
      if (reader.canRead() && reader.peek() == ' ') {
         reader.skip();
         Coordinates coordinateArgument2 = Coordinates.parse(reader, false);
         if (reader.canRead() && reader.peek() == ' ') {
            reader.skip();
            Coordinates coordinateArgument3 = Coordinates.parse(reader, centerIntegers);
            return new DefaultPosArgument(coordinateArgument, coordinateArgument2, coordinateArgument3);
         } else {
            reader.setCursor(i);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
         }
      } else {
         reader.setCursor(i);
         throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext(reader);
      }
   }

   public static DefaultPosArgument absolute(double x, double y, double z) {
      return new DefaultPosArgument(new Coordinates(false, x), new Coordinates(false, y), new Coordinates(false, z));
   }

   public static DefaultPosArgument absolute(Vec2f vec) {
      return new DefaultPosArgument(new Coordinates(false, (double)vec.x), new Coordinates(false, (double)vec.y), new Coordinates(true, 0.0D));
   }

   public static DefaultPosArgument zero() {
      return new DefaultPosArgument(new Coordinates(true, 0.0D), new Coordinates(true, 0.0D), new Coordinates(true, 0.0D));
   }

   public int hashCode() {
      int i = this.x.hashCode();
      i = 31 * i + this.y.hashCode();
      i = 31 * i + this.z.hashCode();
      return i;
   }
}
